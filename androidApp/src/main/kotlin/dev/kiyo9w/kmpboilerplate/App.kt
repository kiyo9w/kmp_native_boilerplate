package dev.kiyo9w.kmpboilerplate

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.kiyo9w.kmpboilerplate.screens.CatalogDetailScreen
import dev.kiyo9w.kmpboilerplate.screens.CatalogListScreen
import kotlinx.serialization.Serializable

@Serializable
object CatalogListDestination

@Serializable
data class CatalogDetailDestination(val itemId: Long)

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    ) {
        Surface {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = CatalogListDestination) {
                composable<CatalogListDestination> {
                    CatalogListScreen(
                        navigateToDetails = { itemId ->
                            navController.navigate(CatalogDetailDestination(itemId))
                        },
                    )
                }
                composable<CatalogDetailDestination> { backStackEntry ->
                    CatalogDetailScreen(
                        itemId = backStackEntry.toRoute<CatalogDetailDestination>().itemId,
                        navigateBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}
