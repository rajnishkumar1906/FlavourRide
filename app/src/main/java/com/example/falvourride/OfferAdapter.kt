package com.example.falvourride

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class OffersAdapter(
    private val context: Context,
    private val offers: List<Offer>,
    private val onRedeemClick: (Offer) -> Unit  // Callback for applying offer
) : RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    inner class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offerImage: ImageView = itemView.findViewById(R.id.offerImage)
        val offerTitle: TextView = itemView.findViewById(R.id.offerTitle)
        val offerDescription: TextView = itemView.findViewById(R.id.offerDescription)
        val redeemBtn: Button = itemView.findViewById(R.id.redeemBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        holder.offerImage.setImageResource(offer.imageResId)
        holder.offerTitle.text = offer.title
        holder.offerDescription.text = offer.description

        holder.redeemBtn.setOnClickListener {
            Toast.makeText(context, "Code ${offer.code} applied!", Toast.LENGTH_SHORT).show()
            onRedeemClick(offer)
        }
    }

    override fun getItemCount(): Int = offers.size
}
