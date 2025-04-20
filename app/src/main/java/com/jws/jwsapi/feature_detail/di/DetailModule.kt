package com.jws.jwsapi.feature_detail.di

import com.jws.jwsapi.feature_detail.data.DetailRepositoryImpl
import com.jws.jwsapi.feature_detail.domain.DetailApi
import com.jws.jwsapi.feature_detail.domain.DetailRepository
import com.jws.jwsapi.feature_preview.domain.PreviewApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @Provides
    @Singleton
    fun provideDetailRepository(
        detailApi: DetailApi
    ): DetailRepository {
        return DetailRepositoryImpl(detailApi)
    }

    @Provides
    @Singleton
    fun provideDetailApi(retrofit: Retrofit): DetailApi {
        return retrofit.create(DetailApi::class.java)
    }

}