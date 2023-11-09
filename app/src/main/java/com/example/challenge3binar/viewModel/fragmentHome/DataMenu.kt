package com.example.challenge3binar.viewModel.fragmentHome

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataMenu(
    val img:Int,
    val nameMenu:String,
    val hargaMenu:String,
    val deskripsi:String,
    val lokasi:String
):Parcelable