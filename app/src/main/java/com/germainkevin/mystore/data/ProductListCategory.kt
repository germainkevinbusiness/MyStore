package com.germainkevin.mystore.data

sealed interface ProductListCategory {
    object AllCategories : ProductListCategory
    object Electronics : ProductListCategory
    object Jewelery : ProductListCategory
    object MenClothing : ProductListCategory
    object WomenClothing : ProductListCategory
}