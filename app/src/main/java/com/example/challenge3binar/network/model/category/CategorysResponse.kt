package com.example.challenge3binar.network.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategorysResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<CategoryResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)