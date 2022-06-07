package com.germainkevin.mystore.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DetailScreenState(var product: Product? = null)

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {

    var detailScreenState = mutableStateOf(DetailScreenState())
        private set

    fun getProductById(productId: Int) = repository.getProductById(productId, viewModelScope) {
        viewModelScope.launch(Dispatchers.Main) {
            detailScreenState.value = detailScreenState.value.copy(product = it)
        }
    }

    fun updateProduct(product: Product) {
        repository.updateProduct(viewModelScope, product) {
            getProductById(product.id)
        }
    }
}