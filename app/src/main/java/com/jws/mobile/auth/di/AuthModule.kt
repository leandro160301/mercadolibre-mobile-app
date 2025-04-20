package com.jws.mobile.auth.di

import com.jws.mobile.auth.data.AuthRepositoryImpl
import com.jws.mobile.auth.domain.AuthApi
import com.jws.mobile.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi
    ): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }
}