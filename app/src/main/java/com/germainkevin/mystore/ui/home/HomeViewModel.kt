package com.germainkevin.mystore.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.data.ProductListCategory
import com.germainkevin.mystore.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    var isLoading: Boolean = false,
    var products: List<Product> = emptyList(),
    var productListCategory: ProductListCategory = ProductListCategory.AllCategories
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {

    var homeScreenState = mutableStateOf(HomeScreenState())
        private set

    fun getProducts(productListCategory: ProductListCategory) {
        repository.getProducts(viewModelScope, productListCategory) { products ->
            viewModelScope.launch(Dispatchers.Main) {
                homeScreenState.value = homeScreenState.value.copy(
                    products = products,
                    isLoading = false,
                    productListCategory = productListCategory
                )
            }
        }
    }

    fun updateProduct(
        product: Product,
        productListCategory: ProductListCategory,
    ) = repository.updateProduct(viewModelScope, product) {
        getProducts(productListCategory = productListCategory)
    }

    init {
        homeScreenState.value = homeScreenState.value.copy(isLoading = true)
        getProducts(productListCategory = ProductListCategory.AllCategories)
    }
}