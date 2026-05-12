package com.deymervilla.usecase.user

import com.deymervilla.repository.repositories.user.UserRepository
import javax.inject.Inject

class FetchUserLocationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.location()
}