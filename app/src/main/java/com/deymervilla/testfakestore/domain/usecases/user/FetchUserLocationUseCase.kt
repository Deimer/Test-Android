package com.deymervilla.testfakestore.domain.usecases.user

import com.deymervilla.testfakestore.domain.repositories.user.UserRepository
import javax.inject.Inject

class FetchUserLocationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.location()
}