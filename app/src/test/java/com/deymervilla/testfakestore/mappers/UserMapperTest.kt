package com.deymervilla.testfakestore.mappers

import com.deymervilla.testfakestore.data.network.dto.UserDTO
import com.deymervilla.testfakestore.domain.mappers.toEntity
import com.deymervilla.testfakestore.domain.mappers.toModel
import com.deymervilla.testfakestore.domain.models.UserModel
import com.deymervilla.testfakestore.dummyAddressDTO
import com.deymervilla.testfakestore.dummyNameDTO
import com.deymervilla.testfakestore.dummyUserDTO
import com.deymervilla.testfakestore.dummyUserEntity
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UserMapperTest {

    @Test
    fun `test UserDTO toModel maps correctly`() {
        val dto = dummyUserDTO(1, "johndoe")
        val model = dto.toModel()
        assertEquals(dto.id, model.id)
        assertEquals(dto.username, model.username)
        assertEquals(dto.email, model.email)
        assertEquals(dto.phone, model.phone)
        assertEquals(dto.address?.number, model.number)
        assertEquals(dto.address?.zipcode, model.zipcode)
        assertEquals(dto.address?.geolocation?.latitude, model.latitude)
        assertEquals(dto.address?.geolocation?.longitude, model.longitude)
    }

    @Test
    fun `test UserDTO toModel capitalizes name and address fields`() {
        val dto = dummyUserDTO(1, "johndoe").copy(
            name = dummyNameDTO(firstName = "john", lastName = "doe"),
            address = dummyAddressDTO(city = "dummy city", street = "dummy street")
        )
        val model = dto.toModel()
        assertEquals("John", model.firstName)
        assertEquals("Doe", model.lastName)
        assertEquals("Dummy City", model.city)
        assertEquals("Dummy Street", model.street)
    }

    @Test
    fun `test UserDTO toModel handles null fields with defaults`() {
        val dto = UserDTO(
            id = null,
            username = null,
            email = null,
            password = null,
            phone = null,
            name = null,
            address = null
        )
        val model = dto.toModel()
        assertEquals(0, model.id)
        assertEquals("", model.username)
        assertEquals("", model.email)
        assertEquals("", model.phone)
        assertEquals("", model.firstName)
        assertEquals("", model.lastName)
        assertEquals("", model.city)
        assertEquals("", model.street)
        assertEquals(0, model.number)
        assertEquals("", model.zipcode)
        assertEquals("", model.latitude)
        assertEquals("", model.longitude)
    }

    @Test
    fun `test UserDTO toEntity maps correctly`() {
        val dto = dummyUserDTO(1, "johndoe")
        val entity = dto.toEntity()
        assertEquals(dto.id, entity.id)
        assertEquals(dto.username, entity.username)
        assertEquals(dto.email, entity.email)
        assertEquals(dto.phone, entity.phone)
        assertEquals(dto.name?.firstName, entity.firstName)
        assertEquals(dto.name?.lastName, entity.lastName)
        assertEquals(dto.address?.city, entity.city)
        assertEquals(dto.address?.street, entity.street)
        assertEquals(dto.address?.number, entity.number)
        assertEquals(dto.address?.zipcode, entity.zipcode)
        assertEquals(dto.address?.geolocation?.latitude, entity.latitude)
        assertEquals(dto.address?.geolocation?.longitude, entity.longitude)
    }

    @Test
    fun `test UserDTO toEntity does not capitalize fields`() {
        val dto = dummyUserDTO(1, "johndoe").copy(
            name = dummyNameDTO(firstName = "john", lastName = "doe"),
            address = dummyAddressDTO(city = "dummy city", street = "dummy street")
        )
        val entity = dto.toEntity()
        assertEquals("john", entity.firstName)
        assertEquals("doe", entity.lastName)
        assertEquals("dummy city", entity.city)
        assertEquals("dummy street", entity.street)
    }

    @Test
    fun `test UserEntity toModel maps correctly`() {
        val entity = dummyUserEntity(1, "johndoe")
        val model = entity.toModel()
        assertEquals(entity.id, model.id)
        assertEquals(entity.username, model.username)
        assertEquals(entity.email, model.email)
        assertEquals(entity.phone, model.phone)
        assertEquals(entity.firstName, model.firstName)
        assertEquals(entity.lastName, model.lastName)
        assertEquals(entity.city, model.city)
        assertEquals(entity.street, model.street)
        assertEquals(entity.number, model.number)
        assertEquals(entity.zipcode, model.zipcode)
        assertEquals(entity.latitude, model.latitude)
        assertEquals(entity.longitude, model.longitude)
    }

    @Test
    fun `test UserModel toEntity maps correctly`() {
        val model = UserModel(
            id = 1,
            username = "johndoe",
            email = "john@dummy.com",
            phone = "123-456-7890",
            firstName = "john",
            lastName = "doe",
            city = "dummy city",
            street = "dummy street",
            number = 42,
            zipcode = "12345",
            latitude = "40.7128",
            longitude = "-74.0060"
        )
        val entity = model.toEntity()
        assertEquals(model.id, entity.id)
        assertEquals(model.username, entity.username)
        assertEquals(model.email, entity.email)
        assertEquals(model.phone, entity.phone)
        assertEquals(model.number, entity.number)
        assertEquals(model.zipcode, entity.zipcode)
        assertEquals(model.latitude, entity.latitude)
        assertEquals(model.longitude, entity.longitude)
    }

    @Test
    fun `test UserModel toEntity capitalizes name and address fields`() {
        val model = UserModel(
            id = 1,
            username = "johndoe",
            firstName = "john",
            lastName = "doe",
            city = "dummy city",
            street = "dummy street"
        )
        val entity = model.toEntity()
        assertEquals("John", entity.firstName)
        assertEquals("Doe", entity.lastName)
        assertEquals("Dummy City", entity.city)
        assertEquals("Dummy Street", entity.street)
    }

    @Test
    fun `test UserModel fullName returns correct value`() {
        val model = UserModel(firstName = "John", lastName = "Doe")
        assertEquals("John Doe", model.fullName)
    }

    @Test
    fun `test UserModel fullName handles blank fields`() {
        assertEquals("John", UserModel(firstName = "John", lastName = "").fullName)
        assertEquals("Doe", UserModel(firstName = "", lastName = "Doe").fullName)
        assertEquals("", UserModel(firstName = "", lastName = "").fullName)
    }

    @Test
    fun `test UserModel fullAddress returns correct value`() {
        val model = UserModel(street = "Main St", number = 42, city = "Springfield")
        assertEquals("Main St 42, Springfield", model.fullAddress)
    }

    @Test
    fun `test UserModel fullAddress handles zero number`() {
        val model = UserModel(street = "Main St", number = 0, city = "Springfield")
        assertEquals("Main St, Springfield", model.fullAddress)
    }

    @Test
    fun `test UserModel fullAddress handles blank city`() {
        val model = UserModel(street = "Main St", number = 42, city = "")
        assertEquals("Main St 42", model.fullAddress)
    }

    @Test
    fun `test UserModel locationMapUrl returns correct value`() {
        val model = UserModel(latitude = "40.7128", longitude = "-74.0060")
        assertEquals("https://www.google.com/maps?q=40.7128,-74.0060", model.locationMapUrl)
    }

    @Test
    fun `test UserModel imageUrl returns pravatar url`() {
        val model = UserModel()
        assertEquals("https://i.pravatar.cc/500", model.imageUrl)
    }
}