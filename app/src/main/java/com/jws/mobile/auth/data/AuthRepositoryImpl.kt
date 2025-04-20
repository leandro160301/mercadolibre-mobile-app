package com.jws.mobile.auth.data

import com.jws.mobile.auth.domain.Auth
import com.jws.mobile.auth.domain.AuthApi
import com.jws.mobile.auth.domain.AuthRepository
import com.jws.mobile.core.network.NetworkResultWrapper
import com.jws.mobile.core.utils.Constants.CLIENT_ID
import com.jws.mobile.core.utils.Constants.VENDOR_ID
import com.jws.mobile.core.utils.Constants.VENDOR_SECRET
import com.jws.mobile.core.utils.Resource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): AuthRepository {

    override suspend fun login(): Resource<Auth> {
        val refreshToken = getRefreshToken()
        return NetworkResultWrapper.getApiResponse {
            authApi.requestAccessToken(
                clientId = CLIENT_ID,
                clientSecret = VENDOR_SECRET,
                refreshToken = refreshToken,
                grantType = "refresh_token"
            )
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) = suspendCoroutine { cont ->
        RefreshTokenFirebase.updateRefreshToken(refreshToken) { success ->
            if (success) cont.resume(Unit)
            else cont.resumeWithException(IllegalStateException("Error al guardar el refresh token"))
        }
    }

    private suspend fun getRefreshToken(): String = suspendCoroutine { cont ->
        RefreshTokenFirebase.getRefreshToken { token ->
            if (token != null) cont.resume(token)
            else cont.resumeWithException(IllegalStateException("No se pudo obtener el refresh token"))
        }
    }
}