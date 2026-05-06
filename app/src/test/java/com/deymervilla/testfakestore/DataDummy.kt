package com.deymervilla.testfakestore

import com.deymervilla.testfakestore.data.database.entities.ProductEntity
import com.deymervilla.testfakestore.data.database.entities.UserEntity
import com.deymervilla.testfakestore.data.network.dto.AddressDTO
import com.deymervilla.testfakestore.data.network.dto.GeolocationDTO
import com.deymervilla.testfakestore.data.network.dto.NameDTO
import com.deymervilla.testfakestore.data.network.dto.ProductDTO
import com.deymervilla.testfakestore.data.network.dto.RatingDTO
import com.deymervilla.testfakestore.data.network.dto.UserDTO

fun dummyProductDTO(
    id: Int = 1,
    title: String = "Dummy Product",
    price: Double = 9.99,
    description: String = "Dummy description",
    category: String = "dummy category",
    image: String = "https://dummy.com/image.jpg",
    rating: RatingDTO? = dummyRatingDTO()
) = ProductDTO(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating
)

fun dummyRatingDTO(
    rate: Double = 4.5,
    count: Int = 100
) = RatingDTO(
    rate = rate,
    count = count
)

fun dummyUserDTO(
    id: Int = 1,
    username: String = "johndoe",
    email: String = "john@dummy.com",
    password: String = "dummypass",
    phone: String = "123-456-7890",
    name: NameDTO? = dummyNameDTO(),
    address: AddressDTO? = dummyAddressDTO()
) = UserDTO(
    id = id,
    username = username,
    email = email,
    password = password,
    phone = phone,
    name = name,
    address = address
)

fun dummyNameDTO(
    firstName: String = "John",
    lastName: String = "Doe"
) = NameDTO(
    firstName = firstName,
    lastName = lastName
)

fun dummyAddressDTO(
    city: String = "Dummy City",
    street: String = "Dummy Street",
    number: Int = 42,
    zipcode: String = "12345",
    geolocation: GeolocationDTO? = dummyGeolocationDTO()
) = AddressDTO(
    city = city,
    street = street,
    number = number,
    zipcode = zipcode,
    geolocation = geolocation
)

fun dummyGeolocationDTO(
    latitude: String = "40.7128",
    longitude: String = "-74.0060"
) = GeolocationDTO(
    latitude = latitude,
    longitude = longitude
)

fun dummyProductEntity(
    id: Int = 1,
    title: String = "Dummy Product",
    price: Double = 9.99,
    description: String = "Dummy description",
    category: String = "dummy category",
    image: String = "https://dummy.com/image.jpg",
    rate: Double = 4.5,
    count: Int = 100,
    isFavorite: Boolean = false
) = ProductEntity(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rate = rate,
    count = count,
    isFavorite = isFavorite
)

fun dummyUserEntity(
    id: Int = 1,
    username: String = "johndoe",
    email: String = "john@dummy.com",
    phone: String = "123-456-7890",
    firstName: String = "John",
    lastName: String = "Doe",
    city: String = "Dummy City",
    street: String = "Dummy Street",
    number: Int = 42,
    zipcode: String = "12345",
    latitude: String = "40.7128",
    longitude: String = "-74.0060"
) = UserEntity(
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
    longitude = longitude
)