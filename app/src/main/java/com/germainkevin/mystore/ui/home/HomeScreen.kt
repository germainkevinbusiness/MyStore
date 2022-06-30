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
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.germainkevin.mystore.ui.drawer.LeftDrawer
import com.germainkevin.mystore.ui.home.components.ChipGroups
import com.germainkevin.mystore.ui.home.components.HomeTopAppBar
import com.germainkevin.mystore.ui.home.components.ProductItem
import com.germainkevin.mystore.utils.NavActions
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
                allProducts = homeScreenState.allProducts,
                navActions,
                openLeftDrawer,
                scrollBehavior
            )
        },
        drawerContent = {
            LeftDrawer(
                allProducts = homeScreenState.allProducts,
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
                    if (homeScreenState.isLoading) {
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
                    if (homeScreenState.allProducts.isNotEmpty()) {
                        ChipGroups(homeScreenState = homeScreenState, homeViewModel = homeViewModel)
                    }
                    //                    else {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(MaterialTheme.colorScheme.background)
//                                .padding(contentPadding),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Text(
//                                text = stringResource(id = R.string.error_retrieving_products),
//                                style = LocalTextStyle.current.copy(
//                                    color = MaterialTheme.colorScheme.onBackground,
//                                    fontSize = 18.sp,
//                                    fontWeight = FontWeight.Normal
//                                )
//                            )
//                        }
//                    }
                }
                items(homeScreenState.allProducts) { product ->
                    ProductItem(modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                        product = product,
                        onProductClick = { navActions.navigateToProductDetail(product.id) },
                        onAddToCart = {
                            homeViewModel.updateProduct(
                                product = product.copy(addedToCart = it),
                                productListCategory = homeScreenState.productListCategory,
                            )
                        },
                        onAddToFavorites = {
                            homeViewModel.updateProduct(
                                product = product.copy(addedAsFavorite = it),
                                productListCategory = homeScreenState.productListCategory,
                            )
                        }
                    )
                }
            }
        }
    )
}