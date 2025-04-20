package com.jws.mobile.feature_preview.di

import com.jws.mobile.feature_preview.data.PreviewRepositoryImpl
import com.jws.mobile.feature_preview.domain.PreviewApi
import com.jws.mobile.feature_preview.domain.PreviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreviewModule {

    @Provides
    @Singleton
    fun providePreviewRepository(
        previewApi: PreviewApi
    ): PreviewRepository {
        return PreviewRepositoryImpl(previewApi)
    }

    @Provides
    @Singleton
    fun providePreviewApi(retrofit: Retrofit): PreviewApi {
        return retrofit.create(PreviewApi::class.java)
    }

}