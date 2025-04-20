package com.jws.mobile.core.features

object TokenManager {
    private var accessToken: String? = null

    fun setToken(token: String) {
        accessToken = token
    }

    fun getToken(): String? = accessToken
}