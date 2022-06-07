package com.germainkevin.mystore.data.api

import com.germainkevin.mystore.data.Product
import retrofit2.Call
import retrofit2.http.GET

interface FakeStoreApi {

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }

    @GET("products")
    fun getProducts(): Call<List<Product>?>

}