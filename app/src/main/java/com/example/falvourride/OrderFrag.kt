package com.example.falvourride

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderFrag : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private val orderViewModel: OrderViewModel by activityViewModels()

    private lateinit var tvTotalPrice: TextView
    private lateinit var btnPurchase: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        recyclerView = view.findViewById(R.id.recyclerOrders)
        recyclerView.layoutManager = LinearLayoutManager(context)

        orderAdapter = OrderAdapter(mutableListOf())
        recyclerView.adapter = orderAdapter

        tvTotalPrice = view.findViewById(R.id.tvTotalPrice)
        btnPurchase = view.findViewById(R.id.btnPurchase)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPurchase.setOnClickListener {
            if (orderAdapter.itemCount > 0) {
                Toast.makeText(requireContext(), "Purchase Successful!", Toast.LENGTH_SHORT).show()
                // You can add logic here to clear orders or proceed to payment
            } else {
                Toast.makeText(requireContext(), "No orders to purchase.", Toast.LENGTH_SHORT).show()
            }
        }

        orderViewModel.orders.observe(viewLifecycleOwner, Observer { newOrders ->
            orderAdapter.updateOrders(newOrders)
            updateTotalPrice(newOrders)
        })
    }

    private fun updateTotalPrice(orders: List<Order>) {
        val total = orders.sumOf { it.price }
        tvTotalPrice.text = "Total: ₹%.2f".format(total)
    }
}
