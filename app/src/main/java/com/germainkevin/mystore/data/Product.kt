package com.germainkevin.mystore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val price: String,
    val category: String,
    val description: String,
    val image: String,
    // When adding to cart
    var addedToCart: Boolean = false,
    // When added as Favorites
    var addedAsFavorite: Boolean = false,
)