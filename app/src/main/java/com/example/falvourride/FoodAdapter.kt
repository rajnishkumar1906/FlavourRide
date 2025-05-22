package com.example.falvourride

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(
    private val items: List<FoodItem>,
    private val onAddClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ImageView = view.findViewById(R.id.foodImage)
        val foodName: TextView = view.findViewById(R.id.foodName)
        val foodPrice: TextView = view.findViewById(R.id.foodPrice)
        val btnAdd: Button = view.findViewById(R.id.addButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = items[position]
        holder.foodImage.setImageResource(item.imageResId)
        holder.foodName.text = item.name
        holder.foodPrice.text = "₹%.2f".format(item.price)
        holder.btnAdd.setOnClickListener { onAddClick(item) }
    }

    override fun getItemCount(): Int = items.size
}
