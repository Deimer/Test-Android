package com.deymervilla.testfakestore.mappers

import com.deymervilla.testfakestore.data.network.dto.ProductDTO
import com.deymervilla.testfakestore.domain.mappers.toEntity
import com.deymervilla.testfakestore.domain.mappers.toModel
import com.deymervilla.testfakestore.dummyProductDTO
import com.deymervilla.testfakestore.dummyProductEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ProductMapperTest {

    @Test
    fun `test ProductDTO toModel maps correctly`() {
        val dto = dummyProductDTO(1, "Dummy Product")
        val model = dto.toModel()
        assertEquals(dto.id, model.id)
        assertEquals(dto.title, model.title)
        assertEquals(dto.price, model.rawPrice)
        assertEquals(dto.description, model.description)
        assertEquals(dto.category, model.category)
        assertEquals(dto.image, model.imageUrl)
        assertEquals(dto.rating?.count, model.ratingCount)
        assertEquals(dto.rating?.rate?.toFloat(), model.ratingLabel)
        assertFalse(model.isFavorite)
    }

    @Test
    fun `test ProductDTO toModel handles null fields with defaults`() {
        val dto = ProductDTO(
            id = null,
            title = null,
            price = null,
            description = null,
            category = null,
            image = null,
            rating = null
        )
        val model = dto.toModel()
        assertEquals(0, model.id)
        assertEquals("", model.title)
        assertEquals(0.0, model.rawPrice)
        assertEquals("", model.description)
        assertEquals("", model.category)
        assertEquals("", model.imageUrl)
        assertEquals(0, model.ratingCount)
        assertEquals(0.0f, model.ratingLabel)
        assertFalse(model.isFavorite)
    }

    @Test
    fun `test ProductDTO toEntity maps correctly`() {
        val dto = dummyProductDTO(1, "Dummy Product")
        val entity = dto.toEntity()
        assertEquals(dto.id, entity.id)
        assertEquals(dto.title, entity.title)
        assertEquals(dto.price, entity.price)
        assertEquals(dto.description, entity.description)
        assertEquals(dto.category, entity.category)
        assertEquals(dto.image, entity.image)
        assertEquals(dto.rating?.rate, entity.rate)
        assertEquals(dto.rating?.count, entity.count)
        assertFalse(entity.isFavorite)
    }

    @Test
    fun `test ProductDTO toEntity handles null fields with defaults`() {
        val dto = ProductDTO(
            id = null,
            title = null,
            price = null,
            description = null,
            category = null,
            image = null,
            rating = null
        )
        val entity = dto.toEntity()
        assertEquals(0, entity.id)
        assertEquals("", entity.title)
        assertEquals(0.0, entity.price)
        assertEquals("", entity.description)
        assertEquals("", entity.category)
        assertEquals("", entity.image)
        assertEquals(0.0, entity.rate)
        assertEquals(0, entity.count)
        assertFalse(entity.isFavorite)
    }

    @Test
    fun `test ProductDTO toEntity always sets isFavorite to false`() {
        val dto = dummyProductDTO(1, "Dummy Product")
        val entity = dto.toEntity()
        assertFalse(entity.isFavorite)
    }

    @Test
    fun `test ProductEntity toModel maps correctly`() {
        val entity = dummyProductEntity(1, "Dummy Product")
        val model = entity.toModel()
        assertEquals(entity.id, model.id)
        assertEquals(entity.title, model.title)
        assertEquals(entity.price, model.rawPrice)
        assertEquals(entity.description, model.description)
        assertEquals(entity.category, model.category)
        assertEquals(entity.image, model.imageUrl)
        assertEquals(entity.count, model.ratingCount)
        assertEquals(entity.rate.toFloat(), model.ratingLabel)
        assertEquals(entity.isFavorite, model.isFavorite)
    }

    @Test
    fun `test ProductEntity toModel preserves isFavorite true`() {
        val entity = dummyProductEntity(1, "Dummy Product", isFavorite = true)
        val model = entity.toModel()
        assertTrue(model.isFavorite)
    }

    @Test
    fun `test ProductEntity toModel preserves isFavorite false`() {
        val entity = dummyProductEntity(1, "Dummy Product", isFavorite = false)
        val model = entity.toModel()
        assertFalse(model.isFavorite)
    }
}