package com.example.falvourride

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {

    private val _orders = MutableLiveData<MutableList<Order>>(mutableListOf())
    val orders: LiveData<MutableList<Order>> get() = _orders

    private val _appliedOffer = MutableLiveData<Offer?>()
    val appliedOffer: LiveData<Offer?> get() = _appliedOffer

    fun addOrder(order: Order) {
        val currentList = _orders.value ?: mutableListOf()
        currentList.add(order)
        _orders.value = currentList
    }

    fun applyOffer(offer: Offer) {
        _appliedOffer.value = offer
    }

    fun getAppliedOfferCode(): String? {
        return _appliedOffer.value?.code
    }

    fun getTotalPrice(): Double {
        val total = _orders.value?.sumOf { it.price } ?: 0.0
        val discount = _appliedOffer.value?.let {
            when (it.code) {
                "FIRST20" -> total * 0.2
                "CASHBACK10" -> total * 0.1
                "SANDWICH15" -> total * 0.15
                "FREESHIP" -> 30.0 // simulated shipping discount
                else -> 0.0
            }
        } ?: 0.0
        return (total - discount).coerceAtLeast(0.0)
    }
}
