package com.germainkevin.mystore.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.germainkevin.mystore.R
import com.germainkevin.mystore.data.ProductListCategory
import com.germainkevin.mystore.ui.drawer.LeftDrawer
import com.germainkevin.mystore.ui.home.components.HomeTopAppBar
import com.germainkevin.mystore.ui.home.components.ProductItem
import com.germainkevin.mystore.ui.home.components.ProductListCategories
import com.germainkevin.mystore.utils.NavActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val homeScreenState by remember { homeViewModel.homeScreenState }
    val context = LocalContext.current
    val categoriesMap: Map<ProductListCategory, String> = remember {
        mapOf(
            ProductListCategory.AllCategories to context.getString(R.string.all_categories),
            ProductListCategory.Electronics to context.getString(R.string.electronics),
            ProductListCategory.Jewelery to context.getString(R.string.jewelery),
            ProductListCategory.MenClothing to context.getString(R.string.men_clothing),
            ProductListCategory.WomenClothing to context.getString(R.string.women_clothing)
        )
    }
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
                products = homeScreenState.products,
                navActions,
                openLeftDrawer,
                scrollBehavior,
            )
        },
        drawerContent = {
            LeftDrawer(
                products = homeScreenState.products,
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
                    if (homeScreenState.products.isNotEmpty()) {
                        ProductListCategories(
                            homeScreenState = homeScreenState,
                            categoriesMap = categoriesMap,
                            onClick = { category ->
                                homeViewModel.getProducts(productListCategory = category)
                            })
                    }
                }
                items(homeScreenState.products) { product ->
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
                            coroutineScope.launch {
                                if (it) {
                                    val snackBarResult =
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = context.getString(R.string.added_to_cart),
                                            actionLabel = "View in cart"
                                        )
                                    when (snackBarResult) {
                                        SnackbarResult.ActionPerformed -> {
                                            navActions.navigateToCart()
                                            cancel()
                                        }
                                        else -> cancel()
                                    }
                                } else {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                    cancel()
                                }
                            }
                        },
                        onAddToFavorites = {
                            homeViewModel.updateProduct(
                                product = product.copy(addedAsFavorite = it),
                                productListCategory = homeScreenState.productListCategory
                            )
                        }
                    )
                }
            }
        }
    )
}