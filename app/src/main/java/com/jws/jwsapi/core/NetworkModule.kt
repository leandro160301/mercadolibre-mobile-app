package com.jws.jwsapi.core

import com.jws.jwsapi.feature_preview.domain.items.ItemsApi
import com.jws.jwsapi.feature_preview.domain.preview.PreviewApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideItemsApi(retrofit: Retrofit): ItemsApi {
        return retrofit.create(ItemsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePreviewApi(retrofit: Retrofit): PreviewApi {
        return retrofit.create(PreviewApi::class.java)
    }
}