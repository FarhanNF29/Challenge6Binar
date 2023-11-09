package com.example.challenge3binar.order

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_data")
data class OrderData(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,

    @ColumnInfo(name = "item_name")
    val itemName: String? = null,

    @ColumnInfo(name = "item_img")
    val itemImage: String? = null,

    @ColumnInfo(name = "item_price")
    val itemPrice: Int? = null,

    @ColumnInfo(name = "item_quantity")
    val itemQuantity: Int = 0
)

