package com.example.challenge3binar

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_cart")
data class DataCart(
//    @PrimaryKey(autoGenerate = true)
//    var itemId: Int = 0,
    @PrimaryKey(autoGenerate = true)
    var itemId: Int = 0,

    @ColumnInfo(name = "item_name")
    var itemName: String? = null,

    @ColumnInfo(name = "item_img")
    var itemImage: String? = null,

    @ColumnInfo(name = "item_price")
    var itemPrice: Int? = null,

    @ColumnInfo(name = "item_quantity")
    var itemQuantity: Int = -1,

    @ColumnInfo(name = "is_deleted")
    var isDeleted: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "simple_chart_table"
    }
}


