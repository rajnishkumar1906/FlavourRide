package com.example.falvourride

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _orders = MutableLiveData<MutableList<Order>>(mutableListOf())
    val orders: LiveData<MutableList<Order>> get() = _orders

    fun addOrder(order: Order) {
        val currentList = _orders.value ?: mutableListOf()
        currentList.add(order)
        _orders.value = currentList
    }
}
