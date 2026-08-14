package dev.kiyo9w.kmpboilerplate.core

import kotlin.test.Test
import kotlin.test.assertEquals

class FlavorTest {
    @Test
    fun fromName_mapsStagingAndProd() {
        assertEquals(AppEnvironment.Staging, Flavor.fromName("staging").environment)
        assertEquals(AppEnvironment.Prod, Flavor.fromName("prod").environment)
        assertEquals(AppEnvironment.Debug, Flavor.fromName("debug").environment)
        assertEquals(AppEnvironment.Debug, Flavor.fromName("anything").environment)
    }

    @Test
    fun threeFlavors_shareThePublicSampleUrl() {
        assertEquals(Flavor.DEFAULT_CATALOG_BASE_URL, Flavor.Debug.catalogBaseUrl)
        assertEquals(Flavor.DEFAULT_CATALOG_BASE_URL, Flavor.Staging.catalogBaseUrl)
        assertEquals(Flavor.DEFAULT_CATALOG_BASE_URL, Flavor.Prod.catalogBaseUrl)
    }
}
