package com.germainkevin.mystore.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.data.ProductListCategory
import com.germainkevin.mystore.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    var isLoading: Boolean = false,
    var products: List<Product> = emptyList(),
    var productListCategory: ProductListCategory = ProductListCategory.AllCategories
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState get() = _homeScreenState.asStateFlow()

    fun getProducts(productListCategory: ProductListCategory) {
        repository.getProducts(viewModelScope, productListCategory) { products ->
            viewModelScope.launch {
                _homeScreenState.value = homeScreenState.value.copy(
                    products = products,
                    isLoading = false,
                    productListCategory = productListCategory
                )
            }
        }
    }

    fun updateProduct(
        product: Product,
        productListCategory: ProductListCategory
    ) = repository.updateProduct(viewModelScope, product) {
        getProducts(productListCategory = productListCategory)
    }

    init {
        _homeScreenState.value = homeScreenState.value.copy(isLoading = true)
        getProducts(productListCategory = ProductListCategory.AllCategories)
    }
}