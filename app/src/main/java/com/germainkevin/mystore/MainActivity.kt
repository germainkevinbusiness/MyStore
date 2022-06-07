package com.germainkevin.mystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.germainkevin.mystore.ui.cart.CartScreen
import com.germainkevin.mystore.ui.detail.DetailScreen
import com.germainkevin.mystore.ui.favorites.FavoritesScreen
import com.germainkevin.mystore.ui.home.HomeScreen
import com.germainkevin.mystore.ui.pay.PayScreen
import com.germainkevin.mystore.ui.theme.MyStoreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyStoreTheme {
                val navController = rememberNavController()
                val navActions = remember(navController) { NavActions(navController) }
                val coroutineScope = rememberCoroutineScope()
                MainScreen(
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }
        }
    }
}

@Composable
private fun MainScreen(navActions: NavActions, coroutineScope: CoroutineScope) {
    val currentBackStackEntry by navActions.navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: NavRoutes.HOME
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navActions.navController,
            startDestination = NavRoutes.HOME
        ) {
            composable(NavRoutes.HOME) {
                HomeScreen(
                    currentRoute = currentRoute,
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }
            composable(
                NavRoutes.PRODUCT_DETAIL + "{productId}",
                arguments = listOf(navArgument(name = "productId") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) { currentBackStackEntry ->
                val productId = currentBackStackEntry.arguments?.getInt("productId") ?: -1
                DetailScreen(
                    navActions = navActions,
                    productId = productId
                )
            }
            composable(NavRoutes.CART) {
                CartScreen(
                    currentRoute = currentRoute,
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }

            composable(NavRoutes.FAVORITES) {
                FavoritesScreen(
                    currentRoute = currentRoute,
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }

            composable(NavRoutes.PAY) {
                PayScreen(navActions)
            }
        }
    }
}