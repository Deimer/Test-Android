package com.deymervilla.usecase.user

import com.deymervilla.repository.models.UserModel
import com.deymervilla.repository.repositories.user.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(user: UserModel) =
        userRepository.update(user)
}