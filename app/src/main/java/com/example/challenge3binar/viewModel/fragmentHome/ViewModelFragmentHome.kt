package com.example.challenge3binar.viewModel.fragmentHome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge3binar.network.model.product.ProductItemResponse
import com.example.challenge3binar.network.model.product.ProductsResponse
import com.example.challenge3binar.network.service.ApiClient
import com.example.challenge3binar.repository.RepositoryMenu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFragmentHome(private val repositoryMenu: RepositoryMenu) : ViewModel(){
//    var _listProduct: MutableLiveData<List<ProductItemResponse>> = MutableLiveData()
//    val listProduct: LiveData<List<ProductItemResponse>> get() = _listProduct

    val listProduct: LiveData<List<ProductItemResponse>> get() = repositoryMenu.listProduct

    fun getListProduct(query: String? = null){
        repositoryMenu.fetchListProduct(query)
    }
}