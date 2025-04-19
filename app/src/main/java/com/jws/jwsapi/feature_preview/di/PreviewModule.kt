package com.jws.jwsapi.feature_preview.di

import com.jws.jwsapi.feature_preview.data.PreviewRepositoryImpl
import com.jws.jwsapi.feature_preview.domain.PreviewRepository
import com.jws.jwsapi.feature_preview.domain.items.ItemsApi
import com.jws.jwsapi.feature_preview.domain.preview.PreviewApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreviewModule {

    @Provides
    @Singleton
    fun providePreviewRepository(
        itemsApi: ItemsApi,
        previewApi: PreviewApi
    ): PreviewRepository {
        return PreviewRepositoryImpl(itemsApi, previewApi)
    }

}