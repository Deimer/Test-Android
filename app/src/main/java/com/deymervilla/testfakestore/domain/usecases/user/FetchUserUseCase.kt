package com.deymervilla.testfakestore.domain.usecases.user

import com.deymervilla.testfakestore.domain.repositories.user.UserRepository
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(userId: Int) =
        userRepository.fetchUser(userId)
}