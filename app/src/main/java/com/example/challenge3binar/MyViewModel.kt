package com.example.challenge3binar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    val vCounter: MutableLiveData<Int> = MutableLiveData(0)
    val counter: LiveData<Int> get() = vCounter

    fun incrementCount(){
        vCounter.postValue(vCounter.value?.plus(1))
    }

    fun decrementCount(){
        vCounter.value?.let {
            if (it > 0){
                vCounter.postValue(vCounter.value?.minus(1))
            }
        }
    }



}