package com.germainkevin.mystore.utils

import androidx.navigation.NavHostController


object NavRoutes {
    const val ABOUT = "About"
    const val HOME = "Home"
    const val PRODUCT_DETAIL = "Product_Detail?productId="
    const val CART = "Cart"
    const val FAVORITES = "Favorites"
    const val PAY = "Pay"
    const val SETTINGS = "Settings"
}
/**
 * Navigate to NavHost composable screens*/
class NavActions(private val navHostController: NavHostController) {
    val navController = navHostController

    val navigateToHome: () -> Unit = {
        navController.navigate(NavRoutes.HOME) {
            popUpTo(NavRoutes.HOME) { inclusive = true }
        }
    }

    val navigateToProductDetail: (Int) -> Unit = {
        navHostController.navigate(NavRoutes.PRODUCT_DETAIL + it)
    }

    val navigateToCart: () -> Unit = {
        navController.navigate(NavRoutes.CART)
    }

    val navigateToFavorites: () -> Unit = {
        navController.navigate(NavRoutes.FAVORITES)
    }

    var navigateToPaySection: () -> Unit = {
        navController.navigate(NavRoutes.PAY)
    }

    var navigateToSettings: () -> Unit = {
        navController.navigate(NavRoutes.SETTINGS)
    }

    var navigateToAbout: () -> Unit = {
        navController.navigate(NavRoutes.ABOUT)
    }
}