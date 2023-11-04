package com.example.challenge3binar.network.service

import com.example.challenge3binar.BuildConfig
import com.example.challenge3binar.network.model.category.CategorysResponse
import com.example.challenge3binar.network.model.order.OrderRequest
import com.example.challenge3binar.network.model.order.OrderResponse
import com.example.challenge3binar.network.model.product.ProductsResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("listmenu")
    fun getProducts(@Query("c") c: String? = null): Call<ProductsResponse>

    @GET("category")
    suspend fun getCategories(): CategorysResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrderResponse

}