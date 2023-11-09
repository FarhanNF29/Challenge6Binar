package com.example.challenge3binar.viewModel.fragmentKeranjang

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3binar.cart.DataCart
import com.example.challenge3binar.database.CartDao
import com.example.challenge3binar.database.DatabaseCart

class ViewModelKeranjang : ViewModel() {
    private lateinit var cartDao: CartDao
    private lateinit var allItems: LiveData<List<DataCart>>

    fun init(cartDao: CartDao) {
        this.cartDao = cartDao
        allItems = cartDao.getAllItem()
    }

    fun getAllItems(): LiveData<List<DataCart>> {
        return allItems
    }

}