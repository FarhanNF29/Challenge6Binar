package com.example.challenge3binar.network.model.order


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OrderItemRequest(
    @SerializedName("catatan")
    val catatan: String?,
    @SerializedName("order_id")
    val orderId: Int?,
    @SerializedName("qty")
    val qty: Int?
)