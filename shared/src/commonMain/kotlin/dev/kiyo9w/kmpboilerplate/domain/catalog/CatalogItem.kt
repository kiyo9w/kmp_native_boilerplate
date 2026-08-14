package dev.kiyo9w.kmpboilerplate.domain.catalog

data class CatalogItem(
    val id: Long,
    val imageUrl: String,
    val breed: String,
    val source: String,
) {
    companion object {
        fun fromDogImageUrl(url: String): CatalogItem {
            val breed = url
                .substringAfter("/breeds/", missingDelimiterValue = "")
                .substringBefore("/", missingDelimiterValue = "unknown")
                .ifBlank { "unknown" }
            return CatalogItem(
                id = url.hashCode().toLong().and(0x7fff_ffff_ffff_ffffL),
                imageUrl = url,
                breed = breed.replace('-', ' '),
                source = "dog.ceo",
            )
        }
    }
}
