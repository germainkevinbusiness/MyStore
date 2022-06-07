package com.germainkevin.mystore.ui.favorites

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.data.repository.ProductListCategory
import com.germainkevin.mystore.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FavoritesScreenState(
    var isLoading: Boolean = true,
    var allProducts: List<Product> = emptyList(),
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {

    var favoritesScreenState = mutableStateOf(FavoritesScreenState())
        private set

    fun getProducts() {
        favoritesScreenState.value = favoritesScreenState.value.copy(isLoading = true)
        repository.getProducts(viewModelScope, ProductListCategory.AllCategories) { products ->
            viewModelScope.launch(Dispatchers.Main) {
                favoritesScreenState.value =
                    favoritesScreenState.value.copy(isLoading = false, allProducts = products)
            }
        }
    }

    init {
        getProducts()
    }
}