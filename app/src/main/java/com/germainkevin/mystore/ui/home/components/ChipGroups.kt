package com.germainkevin.mystore.ui.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.germainkevin.mystore.R
import com.germainkevin.mystore.data.ProductListCategory
import com.germainkevin.mystore.ui.home.HomeScreenState
import com.germainkevin.mystore.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipGroups(homeScreenState: HomeScreenState, homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    val categoriesMap = remember {
        mutableStateOf(
            mapOf(
                ProductListCategory.AllCategories to context.getString(R.string.all_categories),
                ProductListCategory.Electronics to context.getString(R.string.electronics),
                ProductListCategory.Jewelery to context.getString(R.string.jewelery),
                ProductListCategory.MenClothing to context.getString(R.string.men_clothing),
                ProductListCategory.WomenClothing to context.getString(R.string.women_clothing)
            )
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 4.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        for (category in categoriesMap.value) {
            Spacer(modifier = Modifier.width(4.dp))
            FilterChip(
                selected = homeScreenState.productListCategory == category.key,
                onClick = {
                    homeViewModel.getProducts(productListCategory = category.key)
                },
                label = { Text(text = category.value) }
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}