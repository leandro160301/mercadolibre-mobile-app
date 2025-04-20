package com.jws.mobile.auth.domain

import javax.inject.Inject

class UpdateRefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(refreshToken: String) {
        authRepository.updateRefreshToken(refreshToken)
    }
}