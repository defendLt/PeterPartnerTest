package com.platdmit.peterpartnertest.data

import com.platdmit.peterpartnertest.domain.converters.CardConverter
import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.domain.repositories.CardRepo
import com.platdmit.peterpartnertest.domain.repositories.api.ApiCardRepo
import com.platdmit.peterpartnertest.data.retrofit.models.ApiCard
import com.platdmit.peterpartnertest.data.retrofit.models.ApiTransaction
import com.platdmit.peterpartnertest.data.room.dao.BaseDao
import com.platdmit.peterpartnertest.data.room.dao.CardDao
import com.platdmit.peterpartnertest.data.room.dao.TransactionDao
import com.platdmit.peterpartnertest.data.room.entity.DbCard
import com.platdmit.peterpartnertest.data.room.entity.DbTransaction
import com.platdmit.peterpartnertest.domain.converters.TransactionConverter
import com.platdmit.peterpartnertest.domain.model.Transaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.NullPointerException

class CardRepoImpl(
    private val apiCardRepo: ApiCardRepo<ApiCard>,
    private val cardDao: CardDao,
    private val transactionDao: TransactionDao,
    private val cardConverter: CardConverter<ApiCard, DbCard, Card>,
    private val transactionConverter: TransactionConverter<ApiTransaction, DbTransaction, Transaction>
) : CardRepo {
    override fun getCard(number: String): Single<Card> {
        return Single.create<Card>{ emitter ->
            try {
                val card = cardConverter.fromDbToDomain(cardDao.getCard(number)!!)
                transactionDao.getTransactions(number)?.let { dbTransactions ->
                    card.transaction_history =
                        dbTransactions.map { transactionConverter.fromDbToDomain(it) }
                }
                emitter.onSuccess(card)
            } catch (e: NullPointerException) {
                emitter.onError(Throwable("Empty"))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getCard(): Single<Card> {
        return Single.create<Card>{ emitter ->
            try {
                val card = cardConverter.fromDbToDomain(cardDao.getCard()!!)
                transactionDao.getTransactions(card.number)?.let { dbTransactions ->
                    card.transaction_history = dbTransactions.map { transactionConverter.fromDbToDomain(it) }
                }
                emitter.onSuccess(card)
            } catch (e: NullPointerException){
                emitter.onError(Throwable("Empty"))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun getCards(): Single<List<Card>> {
        return Single.create<List<Card>>{ emitter ->
            val cards = cardDao.getCards()?.map { cardConverter.fromDbToDomain(it) }
            if(cards != null){
                emitter.onSuccess(cards)
            } else {
                emitter.onError(Throwable("Empty"))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun refreshCards(): Completable {
        return Completable.create{success ->
            apiCardRepo.getCards()
                .onErrorComplete{
                    success.onError(Throwable("Fall"))
                    true
                }
                .subscribe { apiCards ->
                    cardDao.delCards()
                    apiCards.forEach {
                        val dbCard = cardConverter.fromApiToDb(it)
                        val dbTransactions = it.transaction_history?.map {
                                transaction -> transactionConverter.fromApiToDb(transaction, it.card_number)
                        }
                        dbCard.let { card ->
                            cardDao.insert(card)
                        }
                        dbTransactions?.let { transactions ->
                            transactionDao.insertList(transactions)
                        }
                    }
                success.onComplete()
            }
        }.subscribeOn(Schedulers.io())
    }
}