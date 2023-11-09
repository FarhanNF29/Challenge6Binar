package com.example.challenge3binar.order

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(orderData: OrderData)

    @Query("SELECT * FROM order_data")
    fun getAllOrders(): LiveData<List<OrderData>>

}
