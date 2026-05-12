package com.deymervilla.network.dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("name")
    val name: NameDTO?,
    @SerializedName("address")
    val address: AddressDTO?,
    @SerializedName("phone")
    val phone: String?
)

data class NameDTO(
    @SerializedName("firstname")
    val firstName: String?,
    @SerializedName("lastname")
    val lastName: String?
)

data class AddressDTO(
    @SerializedName("city")
    val city: String?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("zipcode")
    val zipcode: String?,
    @SerializedName("geolocation")
    val geolocation: GeolocationDTO?
)

data class GeolocationDTO(
    @SerializedName("lat")
    val latitude: String?,
    @SerializedName("long")
    val longitude: String?
)