package com.germainkevin.mystore.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 2)
abstract class MyShopDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
}