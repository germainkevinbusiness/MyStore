package com.germainkevin.mystore.data.repository

import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.data.ProductDao
import com.germainkevin.mystore.data.ProductListCategory
import com.germainkevin.mystore.data.api.FakeStoreApi
import com.germainkevin.mystore.utils.getProductsByCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val fakeStoreApi: FakeStoreApi,
    private val productDao: ProductDao,
) : ProductsRepository {

    override fun getProducts(
        coroutineScope: CoroutineScope,
        productListCategory: ProductListCategory,
        result: (List<Product>) -> Unit
    ) {
        coroutineScope.launch((Dispatchers.IO)) {
            productDao.getAllProducts().collectLatest { allProducts ->
                if (allProducts.isEmpty()) {
                    fakeStoreApi.getProducts().enqueue(object : Callback<List<Product>?> {
                        override fun onResponse(
                            call: Call<List<Product>?>,
                            response: Response<List<Product>?>
                        ) {
                            response.body()?.let { products ->
                                products.getProductsByCategory(productListCategory) { prods ->
                                    result(prods)
                                }
                                coroutineScope.launch(Dispatchers.IO) {
                                    productDao.insertProducts(products)
                                }
                            }
                                ?: result(emptyList())
                        }

                        override fun onFailure(call: Call<List<Product>?>, t: Throwable) {
                            result(emptyList())
                        }
                    })
                } else {
                    allProducts.getProductsByCategory(productListCategory) { result(it) }
                }
            }
        }
    }

    override fun updateProduct(
        coroutineScope: CoroutineScope,
        product: Product,
        onProductUpdated: () -> Unit
    ) {
        val job = coroutineScope.launch(Dispatchers.IO) {
            productDao.insertProduct(product)
        }
        if (job.isCompleted) onProductUpdated()
    }

    override fun getProductById(
        productId: Int,
        coroutineScope: CoroutineScope,
        result: (Product) -> Unit
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            val product = productDao.getProductById(productId)
            result(product)
        }
    }
}