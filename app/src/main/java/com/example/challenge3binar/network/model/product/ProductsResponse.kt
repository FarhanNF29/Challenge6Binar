package com.example.challenge3binar.network.model.product


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ProductsResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<ProductItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)