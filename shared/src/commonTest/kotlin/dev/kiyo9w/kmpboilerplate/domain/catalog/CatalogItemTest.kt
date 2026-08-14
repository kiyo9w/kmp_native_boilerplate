package dev.kiyo9w.kmpboilerplate.domain.catalog

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CatalogItemTest {
    @Test
    fun fromDogImageUrl_readsBreedFromPath() {
        val item = CatalogItem.fromDogImageUrl(
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
        )
        assertEquals("hound afghan", item.breed)
        assertEquals("dog.ceo", item.source)
        assertTrue(item.id > 0)
    }

    @Test
    fun fromDogImageUrl_fallsBackWhenPathIsOdd() {
        val item = CatalogItem.fromDogImageUrl("https://example.com/no-breed")
        assertEquals("unknown", item.breed)
    }
}
