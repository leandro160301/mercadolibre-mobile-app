package com.jws.mobile.auth.domain

import com.jws.mobile.core.utils.Resource

interface AuthRepository {
    suspend fun login(): Resource<Auth>
    suspend fun updateRefreshToken(refreshToken: String)
}