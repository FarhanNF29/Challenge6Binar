package com.example.challenge3binar.network.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoryResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("slug")
    val slug: String?
)