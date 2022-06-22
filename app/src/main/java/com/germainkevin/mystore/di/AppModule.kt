package com.germainkevin.mystore.di

import android.app.Application
import androidx.room.Room
import com.germainkevin.mystore.data.MyShopDatabase
import com.germainkevin.mystore.data.api.FakeStoreApi
import com.germainkevin.mystore.data.repository.ProductsRepository
import com.germainkevin.mystore.data.repository.ProductsRepositoryImpl
import com.germainkevin.mystore.utils.PersistentStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePersistentStorage(application: Application): PersistentStorage {
        return PersistentStorage(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(FakeStoreApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideFakeStoreApi(retrofit: Retrofit) = retrofit.create<FakeStoreApi>()

    @Provides
    @Singleton
    fun provideMyShopDatabase(application: Application): MyShopDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            MyShopDatabase::class.java,
            "my_shop"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        fakeStoreApi: FakeStoreApi,
        db: MyShopDatabase,
    ): ProductsRepository {
        return ProductsRepositoryImpl(fakeStoreApi = fakeStoreApi, productDao = db.productDao)
    }
}