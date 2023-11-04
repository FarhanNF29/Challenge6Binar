package com.example.challenge3binar.network.model.order


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OrderRequest(
    @SerializedName("orders")
    val orders: List<OrderItemRequest?>?
)