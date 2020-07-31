package com.platdmit.peterpartnertest.app.screens.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.platdmit.peterpartnertest.domain.model.Card
import com.platdmit.peterpartnertest.R
import com.platdmit.peterpartnertest.app.utilities.ShowMessageHandler
import com.platdmit.peterpartnertest.app.utilities.enums.MessageType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cards.*

@AndroidEntryPoint
class CardsFragment : Fragment(R.layout.fragment_cards), ShowMessageHandler {
    private val cardsAdapter = CardsAdapter()
    private val cardsViewModel : CardsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cards_recycler_view.layoutManager = LinearLayoutManager(context)

        cardsViewModel.cardsLiveData.observe(viewLifecycleOwner, Observer { bindAdapterData(it) })

        cardsViewModel.messageLiveData.observe(viewLifecycleOwner, Observer { bindMessageData(it) })
    }

    private fun bindAdapterData(cards: List<Card>){
        cardsAdapter.setData(cards)
        if(cards_recycler_view.adapter == null){
            cards_recycler_view.adapter = cardsAdapter
        }
    }

    private fun bindMessageData(messageType: MessageType){
        when(messageType){
            MessageType.EMPTY -> showMessage(getString(R.string.message_empty))
            MessageType.FALL -> showMessage(getString(R.string.message_fall))
            MessageType.FALL_CONNECT -> showMessage(getString(R.string.message_fall_connect))
        }
    }

    override fun showMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }
}