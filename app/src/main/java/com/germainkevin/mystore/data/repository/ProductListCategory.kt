package com.germainkevin.mystore.data.repository

sealed class ProductListCategory {
    object AllCategories : ProductListCategory()
    object Electronics : ProductListCategory()
    object Jewelery : ProductListCategory()
    object MenClothing : ProductListCategory()
    object WomenClothing : ProductListCategory()
}