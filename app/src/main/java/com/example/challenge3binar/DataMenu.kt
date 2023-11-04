package com.example.challenge3binar

import android.os.Parcel
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