package com.germainkevin.mystore.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.germainkevin.mystore.utils.NavActions
import com.germainkevin.mystore.utils.NavRoutes
import com.germainkevin.mystore.R
import com.germainkevin.mystore.data.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeftDrawer(
    allProducts: List<Product>,
    currentRoute: String,
    navActions: NavActions,
    closeLeftDrawer: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val itemsInCart = allProducts.filter { it.addedToCart }
            val favoriteItems = allProducts.filter { it.addedAsFavorite }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationDrawerItem(
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home icon") },
                label = { Text(text = stringResource(id = R.string.home)) },
                selected = currentRoute == NavRoutes.HOME,
                onClick = {
                    navActions.navigateToHome()
                    closeLeftDrawer()
                },
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                badge = { Text(text = allProducts.size.toString()) },
                modifier = Modifier.padding(end = 16.dp, bottom = 12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart icon"
                    )
                },
                label = { Text(text = stringResource(id = R.string.cart)) },
                selected = currentRoute == NavRoutes.CART,
                onClick = {
                    navActions.navigateToCart()
                    closeLeftDrawer()
                },
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                badge = { Text(text = itemsInCart.size.toString()) },
                modifier = Modifier.padding(end = 16.dp, top = 12.dp, bottom = 12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite products icon"
                    )
                },
                label = { Text(text = stringResource(id = R.string.favorites)) },
                selected = currentRoute == NavRoutes.FAVORITES,
                onClick = {
                    navActions.navigateToFavorites()
                    closeLeftDrawer()
                },
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                badge = { Text(text = favoriteItems.size.toString()) },
                modifier = Modifier.padding(end = 16.dp, top = 12.dp, bottom = 12.dp)
            )
        }

        IconButton(
            onClick = { navActions.navigateToSettings() }, modifier = Modifier
                .align(
                    Alignment.BottomEnd
                )
                .padding(horizontal = 18.dp, vertical = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.action_settings) + " icon",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}