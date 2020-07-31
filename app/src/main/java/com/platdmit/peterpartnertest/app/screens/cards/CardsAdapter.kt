package com.platdmit.peterpartnertest.app.screens.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.platdmit.peterpartnertest.R
import com.platdmit.peterpartnertest.app.utilities.enums.CardBundleType
import com.platdmit.peterpartnertest.domain.enums.CardLogoType
import com.platdmit.peterpartnertest.domain.model.Card

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.CardsViewHolder>(){
    private val cardsList : MutableList<Card> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val layoutType = R.layout.fragment_cards_item
        return CardsViewHolder(LayoutInflater.from(parent.context).inflate(layoutType, parent, false))
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bindData(cardsList[position])
    }

    fun setData(cards : List<Card>){
        cardsList.clear()
        cardsList.addAll(cards)
        notifyDataSetChanged()
    }

    inner class CardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val cardLogo = itemView.findViewById<ImageView>(R.id.card_logo)
        private val number = itemView.findViewById<TextView>(R.id.card_number)
        private val active = itemView.findViewById<CheckBox>(R.id.card_active)
        fun bindData(card : Card){
            number.text = card.number
            active.isChecked = card.select
            cardLogo.setImageResource(getImage(card.type))

            itemView.setOnClickListener {
                if(card.select){
                    it.findNavController().popBackStack()
                } else {
                    it.findNavController().navigate(R.id.action_cardsFragment_to_mainFragment, bundleOf(
                        CardBundleType.SET_ACTIVE_CARD.name to card.number ))
                }
            }
        }

        private fun getImage(cardLogoType: CardLogoType) : Int{
            return when(cardLogoType){
                CardLogoType.MASTERCARD -> R.drawable.ic_mastercard
                CardLogoType.VISA -> R.drawable.ic_visa
                CardLogoType.UNIONPAY -> R.drawable.ic_union
                CardLogoType.UNKNOWN -> R.drawable.background_box_w
            }
        }
    }
}