package com.deymervilla.usecase.user

import com.deymervilla.repository.repositories.user.UserRepository
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(userId: Int) =
        userRepository.fetch(userId)
}