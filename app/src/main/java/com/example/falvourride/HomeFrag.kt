package com.example.falvourride

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class HomeFrag : Fragment() {

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.foods)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val foodList = listOf(
            FoodItem("Pizza", R.drawable.pizza),
            FoodItem("Burger", R.drawable.burger),
            FoodItem("Cold Drink", R.drawable.cold_drink),
            FoodItem("Sandwich", R.drawable.sandwich)
        )

        val adapter = FoodAdapter(foodList) { foodItem ->
            val newOrder = Order(
                orderId = UUID.randomUUID().toString().substring(0, 8),
                itemName = foodItem.name,
                status = "Pending"
            )
            orderViewModel.addOrder(newOrder)
        }

        recyclerView.adapter = adapter
    }
}
