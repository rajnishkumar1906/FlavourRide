package com.example.falvourride

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OffersFrag : Fragment() {

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerOffers)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val offerList = listOf(
            Offer("20% OFF on first order", "Use code FIRST20. Valid till 31 Dec.", R.drawable.offer1, "FIRST20"),
            Offer("Free Delivery", "On orders over ₹30. Loyalty members no minimum.", R.drawable.offer2, "FREESHIP"),
            Offer("Buy 1 Get 1 Free", "On burgers this weekend only.", R.drawable.offer3, "B1G1BURGER"),
            Offer("10% Cashback", "Cashback credited within 24h.", R.drawable.offer4, "CASHBACK10"),
            Offer("15% OFF Sandwiches", "Use code SANDWICH15 till month end.", R.drawable.offer5, "SANDWICH15")
        )

        val adapter = OffersAdapter(requireContext(), offerList) { selectedOffer ->
            orderViewModel.applyOffer(selectedOffer)
        }

        recyclerView.adapter = adapter
    }
}
