package com.germainkevin.mystore.ui.cart

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

data class CartScreenState(
    var isLoading: Boolean = true,
    var allProducts: List<Product> = emptyList(),
)

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {

    var cartScreenState = mutableStateOf(CartScreenState())
        private set

    fun getProducts() {
        cartScreenState.value = cartScreenState.value.copy(isLoading = true)
        repository.getProducts(viewModelScope, ProductListCategory.AllCategories) { products ->
            viewModelScope.launch(Dispatchers.Main) {
                cartScreenState.value =
                    cartScreenState.value.copy(isLoading = false, allProducts = products)
            }
        }
    }

    fun updateProduct(product: Product) {
        repository.updateProduct(viewModelScope, product = product) {
            getProducts()
        }
    }

    init {
        getProducts()
    }
}