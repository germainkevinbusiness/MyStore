package com.germainkevin.mystore.ui.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.germainkevin.mystore.R
import com.germainkevin.mystore.data.repository.ProductListCategory
import com.germainkevin.mystore.ui.home.HomeScreenState
import com.germainkevin.mystore.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipGroups(homeScreenState: State<HomeScreenState>, homeViewModel: HomeViewModel) {
    val categoriesMap = mapOf(
        ProductListCategory.AllCategories to stringResource(id = R.string.all_categories),
        ProductListCategory.Electronics to stringResource(id = R.string.electronics),
        ProductListCategory.Jewelery to stringResource(id = R.string.jewelery),
        ProductListCategory.MenClothing to stringResource(id = R.string.men_clothing),
        ProductListCategory.WomenClothing to stringResource(id = R.string.women_clothing)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 4.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        for ((category, categoryName) in categoriesMap) {
            Spacer(modifier = Modifier.width(4.dp))
            FilterChip(
                selected = homeScreenState.value.productListCategory == category,
                onClick = {
                    homeViewModel.getProducts(productListCategory = category)
                },
                label = { Text(text = categoryName) }
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}