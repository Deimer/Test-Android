package com.deymervilla.repository.models

data class UserModel(
    val id: Int = 0,
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val city: String = "",
    val street: String = "",
    val number: Int = 0,
    val zipcode: String = "",
    val latitude: String = "",
    val longitude: String = "",
) {
    val fullName: String
        get() = listOf(firstName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")

    val fullAddress: String
        get() = buildString {
            append(street)
            if (number > 0) append(" $number")
            if (city.isNotBlank()) append(", $city")
        }

    val imageUrl: String
        get() = "https://i.pravatar.cc/500"

    val locationMapUrl: String
        get() = "https://www.google.com/maps?q=$latitude,$longitude"
}