package com.deymervilla.repository.mappers

import com.deymervilla.database.entities.UserEntity
import com.deymervilla.network.dto.UserDTO
import com.deymervilla.repository.models.UserModel
import com.deymervilla.repository.utils.capitalizeWords
import com.deymervilla.repository.utils.orZero

fun UserDTO.toModel() = UserModel(
    id = id.orZero(),
    username = username.orEmpty(),
    email = email.orEmpty(),
    phone = phone.orEmpty(),
    firstName = name?.firstName.capitalizeWords(),
    lastName = name?.lastName.capitalizeWords(),
    city = address?.city.capitalizeWords(),
    street = address?.street.capitalizeWords(),
    number = address?.number.orZero(),
    zipcode = address?.zipcode.orEmpty(),
    latitude = address?.geolocation?.latitude.orEmpty(),
    longitude = address?.geolocation?.longitude.orEmpty(),
)

fun UserDTO.toEntity(): UserEntity {
    return UserEntity(
        id = id.orZero(),
        email = email.orEmpty(),
        username = username.orEmpty(),
        phone = phone.orEmpty(),
        firstName = name?.firstName.orEmpty(),
        lastName = name?.lastName.orEmpty(),
        city = address?.city.orEmpty(),
        street = address?.street.orEmpty(),
        number = address?.number.orZero(),
        zipcode = address?.zipcode.orEmpty(),
        latitude = address?.geolocation?.latitude.orEmpty(),
        longitude = address?.geolocation?.longitude.orEmpty()
    )
}

fun UserEntity.toModel() = UserModel(
    id = id,
    username = username,
    email = email,
    phone = phone,
    firstName = firstName,
    lastName = lastName,
    city = city,
    street = street,
    number = number,
    zipcode = zipcode,
    latitude = latitude,
    longitude = longitude,
)

fun UserModel.toEntity() = UserEntity(
    id = id,
    email = email,
    username = username,
    phone = phone,
    firstName = firstName.capitalizeWords(),
    lastName = lastName.capitalizeWords(),
    city = city.capitalizeWords(),
    street = street.capitalizeWords(),
    number = number,
    zipcode = zipcode,
    latitude = latitude,
    longitude = longitude,
)