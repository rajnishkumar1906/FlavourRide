package com.example.falvourride

data class Order(
    val orderId: String,
    val itemName: String,
    val status: String,
    val price: Double,
    val imageResId: Int    // Add this line
)
