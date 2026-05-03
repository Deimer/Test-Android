package com.deymervilla.testfakestore.domain.mappers

import com.deymervilla.testfakestore.data.database.entities.UserEntity
import com.deymervilla.testfakestore.data.network.dto.UserDTO
import com.deymervilla.testfakestore.domain.models.UserModel
import com.deymervilla.testfakestore.domain.utils.orZero

fun UserDTO.toModel(): UserModel {
    val safeFirstName = name?.firstName.orEmpty().replaceFirstChar { it.uppercase() }
    val safeLastName = name?.lastName.orEmpty().replaceFirstChar { it.uppercase() }
    val safeStreet = address?.street.orEmpty()
    val safeNumber = address?.number.orZero()
    val safeCity = address?.city.orEmpty()

    return UserModel(
        id = id.orZero(),
        username = "@${username.orEmpty()}",
        email = email.orEmpty(),
        phone = phone.orEmpty(),
        fullName = "$safeFirstName $safeLastName".trim(),
        fullAddress = "$safeStreet $safeNumber, $safeCity",
        postalCode = "CP: ${address?.zipcode.orEmpty()}",
        locationMapUrl = "https://www.google.com/maps/search/?api=1&query=${address?.geolocation?.latitude},${address?.geolocation?.longitude}"
    )
}

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

fun UserEntity.toModel(): UserModel {
    return UserModel(
        id = id,
        username = "@$username",
        email = email,
        phone = phone,
        fullName = "${firstName.replaceFirstChar { it.uppercase() }} ${lastName.replaceFirstChar { it.uppercase() }}",
        fullAddress = "$street $number, $city",
        postalCode = "CP: $zipcode",
        locationMapUrl = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
    )
}