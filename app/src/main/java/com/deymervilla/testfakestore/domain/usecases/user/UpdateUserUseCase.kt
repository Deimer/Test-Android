package com.deymervilla.testfakestore.domain.usecases.user

import com.deymervilla.testfakestore.domain.models.UserModel
import com.deymervilla.testfakestore.domain.repositories.user.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(user: UserModel) =
        userRepository.update(user)
}