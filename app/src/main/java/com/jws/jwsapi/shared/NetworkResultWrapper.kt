package com.jws.jwsapi.shared

import retrofit2.Response

object NetworkResultWrapper {
    suspend fun <T> getApiResponse(
        apiCall: suspend () -> Response<T>
    ): Resource<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                Resource.Error(error = errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error(error = e.localizedMessage ?: "Exception occurred")
        }
    }
}