package com.example.challenge3binar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataCart::class, OrderData::class], version = 2, exportSchema = false)
abstract class DatabaseCart : RoomDatabase() {
    abstract val simpleChartDao: CartDao
    abstract val orderDao: OrderDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseCart? = null

        fun getInstance(context: Context): DatabaseCart {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseCart::class.java,
                        "simple_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
