package com.germainkevin.mystore.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.germainkevin.mystore.ui.home.components.ChipGroups
import com.germainkevin.mystore.ui.home.components.HomeTopAppBar
import com.germainkevin.mystore.ui.home.components.ProductItem
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.germainkevin.mystore.NavActions
import com.germainkevin.mystore.ui.drawer.LeftDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val homeScreenState = remember { homeViewModel.homeScreenState }
    val openLeftDrawer: () -> Unit = {
        coroutineScope.launch { scaffoldState.drawerState.open() }
    }
    val closeLeftDrawer: () -> Unit = {
        coroutineScope.launch { scaffoldState.drawerState.close() }
    }

    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(
        isAlwaysCollapsed = true,
        centeredTitleAndSubtitle = false
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            HomeTopAppBar(
                allProducts = homeScreenState.value.allProducts,
                navActions,
                openLeftDrawer,
                scrollBehavior
            )
        },
        drawerContent = {
            LeftDrawer(
                allProducts = homeScreenState.value.allProducts,
                currentRoute,
                navActions,
                closeLeftDrawer
            )
        },
        content = { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(contentPadding)
            ) {
                item {
                    if (homeScreenState.value.isLoading) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                item {
                    if (homeScreenState.value.allProducts.isNotEmpty()) {
                        ChipGroups(homeScreenState = homeScreenState, homeViewModel = homeViewModel)
                    }
                }
                items(homeScreenState.value.allProducts) { product ->
                    ProductItem(modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                        product = product,
                        onProductClick = { navActions.navigateToProductDetail(product.id) },
                        onAddToCart = {
                            homeViewModel.updateProduct(
                                product = product.copy(addedToCart = it),
                                productListCategory = homeScreenState.value.productListCategory,
                            )
                        },
                        onAddToFavorites = {
                            homeViewModel.updateProduct(
                                product = product.copy(addedAsFavorite = it),
                                productListCategory = homeScreenState.value.productListCategory,
                            )
                        }
                    )
                }
            }
        }
    )
}