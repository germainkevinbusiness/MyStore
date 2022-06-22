package com.germainkevin.mystore.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.data.ProductListCategory

/**
 * List of possible product categories coming from the remote FakeStore API*/
val allProductCategories = listOf(
    "electronics",
    "jewelery",
    "men's clothing",
    "women's clothing"
)

fun sendUserToSpecificWebPage(url: String, activity: Activity) {
    val openBrowserIntent = Intent(Intent.ACTION_VIEW)
    openBrowserIntent.data = Uri.parse(url)
    activity.startActivity(openBrowserIntent)
}

fun List<Product>.getProductsByCategory(
    productListCategory: ProductListCategory,
    result: (List<Product>) -> Unit
) = when (productListCategory) {
    is ProductListCategory.AllCategories -> result(this)
    is ProductListCategory.Electronics -> {
        val newProducts =
            this.filter { product -> product.category == allProductCategories[0] }
        result(newProducts)
    }
    is ProductListCategory.Jewelery -> {
        val newProducts = this.filter { product ->
            product.category == allProductCategories[1]
        }
        result(newProducts)
    }
    is ProductListCategory.MenClothing -> {
        val newProducts = this.filter { product ->
            product.category == allProductCategories[2]
        }
        result(newProducts)
    }
    is ProductListCategory.WomenClothing -> {
        val newProducts = this.filter { product ->
            product.category == allProductCategories[3]
        }
        result(newProducts)
    }
}