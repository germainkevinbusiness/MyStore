package com.germainkevin.mystore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.data.repository.ProductListCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PersistentStorage(private val context: Context) {

    companion object {
        val IS_USING_DYNAMIC_THEME = booleanPreferencesKey("isUsingDynamicTheme")
    }

    suspend fun setUsingDynamicThemeState(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_USING_DYNAMIC_THEME] = value
            Timber.d("IS_USING_DYNAMIC_THEME: ${settings[IS_USING_DYNAMIC_THEME]}")
        }
    }

    val isUsingDynamicTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        Timber.d("IS_USING_DYNAMIC_THEME: ${preferences[IS_USING_DYNAMIC_THEME]}")
        // No type safety.
        preferences[IS_USING_DYNAMIC_THEME] ?: false
    }

}

/**
 * List of possible product categories coming from the remote FakeStore API*/
val allProductCategories = listOf(
    "electronics",
    "jewelery",
    "men's clothing",
    "women's clothing"
)

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