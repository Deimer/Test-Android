package com.deymervilla.testfakestore.domain.models

data class UserModel(
    val id: Int,
    val username: String,
    val email: String,
    val phone: String,
    val fullName: String,
    val fullAddress: String,
    val postalCode: String,
    val locationMapUrl: String
)
