package com.example.challenge3binar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3binar.network.model.product.ProductItemResponse
import com.example.challenge3binar.network.model.product.ProductsResponse
import com.example.challenge3binar.network.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFragmentHome : ViewModel(){
    var _listProduct: MutableLiveData<List<ProductItemResponse>> = MutableLiveData()
    val listProduct: LiveData<List<ProductItemResponse>> get() = _listProduct

    fun getListProduct(query: String? = null){
        ApiClient.instance.getProducts(query).enqueue(object : Callback<ProductsResponse>{
            override fun onResponse(
                call: Call<ProductsResponse>,
                response: Response<ProductsResponse>
            ) {
                if (response.isSuccessful){
                    _listProduct.postValue(response.body()?.data!!)

                }else{
                    Log.i("failProduct", response.message())
                }
            }

            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {

            }

        })
    }
}