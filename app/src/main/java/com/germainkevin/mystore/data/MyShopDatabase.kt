package com.germainkevin.mystore.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class MyShopDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
}