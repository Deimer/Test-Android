package com.deymervilla.testfakestore

import com.deymervilla.testfakestore.data.database.entities.ProductEntity
import com.deymervilla.testfakestore.data.database.entities.UserEntity

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