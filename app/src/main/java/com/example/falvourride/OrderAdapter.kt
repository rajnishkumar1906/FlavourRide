package com.example.falvourride

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private var orders: MutableList<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderId: TextView = itemView.findViewById(R.id.tvOrderId)
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val ivFoodImage: ImageView = itemView.findViewById(R.id.ivFoodImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.tvOrderId.text = "Order ID: ${order.orderId}"
        holder.tvItemName.text = order.itemName
        holder.tvStatus.text = order.status
        holder.tvPrice.text = "Price: ₹%.2f".format(order.price)
        holder.ivFoodImage.setImageResource(order.imageResId) // Set the image
    }

    override fun getItemCount(): Int = orders.size

    fun updateOrders(newOrders: List<Order>) {
        orders.clear()
        orders.addAll(newOrders)
        notifyDataSetChanged()
    }
}
