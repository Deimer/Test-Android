package com.deymervilla.testfakestore.domain.mappers

import com.deymervilla.testfakestore.data.database.entities.UserEntity
import com.deymervilla.testfakestore.data.network.dto.UserDTO
import com.deymervilla.testfakestore.domain.models.UserModel
import com.deymervilla.testfakestore.domain.utils.orZero

fun UserDTO.toModel() = UserModel(
    id = id.orZero(),
    username = username.orEmpty(),
    email = email.orEmpty(),
    phone = phone.orEmpty(),
    firstName = name?.firstName.orEmpty(),
    lastName = name?.lastName.orEmpty(),
    city = address?.city.orEmpty(),
    street = address?.street.orEmpty(),
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
    firstName = firstName,
    lastName = lastName,
    city = city,
    street = street,
    number = number,
    zipcode = zipcode,
    latitude = latitude,
    longitude = longitude,
)