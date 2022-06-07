package com.germainkevin.mystore.ui.cart

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
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.germainkevin.mystore.NavActions
import com.germainkevin.mystore.ui.cart.components.CartProductItem
import com.germainkevin.mystore.ui.cart.components.CartTopAppBar
import com.germainkevin.mystore.ui.drawer.LeftDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val openLeftDrawer: () -> Unit = { coroutineScope.launch { scaffoldState.drawerState.open() } }
    val closeLeftDrawer: () -> Unit =
        { coroutineScope.launch { scaffoldState.drawerState.close() } }

    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(
        centeredTitleAndSubtitle = false
    )

    val addedToCartProducts =
        cartViewModel.cartScreenState.value.allProducts.filter { it.addedToCart }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            CartTopAppBar(
                itemsInCart = addedToCartProducts,
                navActions,
                openLeftDrawer,
                scrollBehavior,
            )
        },
        drawerContent = {
            LeftDrawer(
                allProducts = cartViewModel.cartScreenState.value.allProducts,
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
                items(addedToCartProducts) { product ->
                    CartProductItem(
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