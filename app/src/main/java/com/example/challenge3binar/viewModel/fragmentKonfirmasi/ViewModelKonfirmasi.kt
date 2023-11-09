package com.example.challenge3binar.viewModel.fragmentKonfirmasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3binar.cart.DataCart
import com.example.challenge3binar.cart.DataCartAdapter
import com.example.challenge3binar.database.CartDao
import com.example.challenge3binar.network.model.order.OrderItemRequest
import com.example.challenge3binar.network.model.order.OrderRequest
import com.example.challenge3binar.network.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelKonfirmasi : ViewModel() {

    private val _totalHarga = MutableLiveData<Int>()
    val totalHarga: LiveData<Int>
        get() = _totalHarga

    private val _pesanBerhasilEvent = MutableLiveData<Boolean>()
    val pesanBerhasilEvent: LiveData<Boolean>
        get() = _pesanBerhasilEvent

    private val _errorEvent = MutableLiveData<String>()
    val errorEvent: LiveData<String>
        get() = _errorEvent

    fun hitungTotalHarga(dataCartList: List<DataCart>) {
        var totalHarga = 0
        for (item in dataCartList) {
            val itemTotalPrice = item.itemPrice?.times(item.itemQuantity) ?: 0
            totalHarga += itemTotalPrice
        }
        _totalHarga.value = totalHarga
    }

    fun pesanBerhasil(dataCartAdapter: DataCartAdapter, dataCartDao: CartDao) {
        val pesananList = dataCartAdapter.getDataCartList()
        val orderItems = mutableListOf<OrderItemRequest>()
        for (item in pesananList) {
            val orderItem = OrderItemRequest(
                catatan = item.itemName,
                orderId = item.itemId,
                qty = item.itemQuantity
            )
            orderItems.add(orderItem)
        }
        val orderRequest = OrderRequest(orders = orderItems)

        val apiService = ApiClient.instance
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.createOrder(orderRequest)
                if (response.code == 201 && response.status == true) {
                    withContext(Dispatchers.Main) {
                        _pesanBerhasilEvent.value = true
                        // Hapus semua item di keranjang setelah pesanan berhasil
                        dataCartDao.deleteAllItems()
                    }
                } else {
                    // Menghandle error response
                    withContext(Dispatchers.Main) {
                        _errorEvent.value = "Gagal membuat pesanan"
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle network or other exceptions
                withContext(Dispatchers.Main) {
                    _errorEvent.value = "Terjadi kesalahan"
                }
            }
        }
    }
}
