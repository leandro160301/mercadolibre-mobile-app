package com.jws.mobile.feature_search.di

import com.jws.mobile.core.helpers.PreferencesHelper
import com.jws.mobile.feature_search.data.SearchRepositoryImpl
import com.jws.mobile.feature_search.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchRepository(
        preferencesHelper: PreferencesHelper
    ): SearchRepository {
        return SearchRepositoryImpl(preferencesHelper)
    }

}