package com.germainkevin.mystore.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.germainkevin.mystore.ui.favorites.components.FavoriteProductItem
import com.germainkevin.mystore.ui.favorites.components.FavoritesTopAppBar
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.germainkevin.mystore.NavActions
import com.germainkevin.mystore.ui.drawer.LeftDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun FavoritesScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val openLeftDrawer: () -> Unit = { coroutineScope.launch { scaffoldState.drawerState.open() } }
    val closeLeftDrawer: () -> Unit =
        { coroutineScope.launch { scaffoldState.drawerState.close() } }

    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(centeredTitleAndSubtitle = false)

    val addedAsFavoriteProducts =
        favoritesViewModel.favoritesScreenState.value.allProducts.filter { it.addedAsFavorite }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            FavoritesTopAppBar(
                favoriteItems = addedAsFavoriteProducts,
                navActions,
                openLeftDrawer,
                scrollBehavior,
            )
        },
        drawerContent = {
            LeftDrawer(
                allProducts = favoritesViewModel.favoritesScreenState.value.allProducts,
                currentRoute,
                navActions,
                closeLeftDrawer
            )
        },
        content = { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(addedAsFavoriteProducts) { product ->
                    FavoriteProductItem(
                        modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 12.dp)
                            .fillMaxWidth(),
                        product = product
                    )
                }
            }
        }
    )
}