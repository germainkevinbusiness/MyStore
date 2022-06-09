package com.germainkevin.mystore.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.germainkevin.mystore.utils.NavActions
import com.germainkevin.mystore.R
import com.germainkevin.mystore.ui.detail.components.DetailItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    productId: Int,
    navActions: NavActions,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarScrollState())
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(id = R.string.detail),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navActions.navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back_button_desc),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            LaunchedEffect(key1 = true) {
                detailViewModel.getProductById(productId)
            }
            detailViewModel.detailScreenState.value.product?.let { product ->
                DetailItem(modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                    product = product, onAddToCart = {
                        detailViewModel.updateProduct(product = product.copy(addedToCart = it))
                    },
                    onAddToFavorites = {
                        detailViewModel.updateProduct(product = product.copy(addedAsFavorite = it))
                    })
            }
        }
    }
}