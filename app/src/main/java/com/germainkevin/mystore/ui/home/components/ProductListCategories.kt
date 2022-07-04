package com.germainkevin.mystore.ui.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.germainkevin.mystore.data.ProductListCategory
import com.germainkevin.mystore.ui.home.HomeScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListCategories(
    homeScreenState: HomeScreenState,
    onClick: (ProductListCategory) -> Unit,
    categoriesMap: Map<ProductListCategory, String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 4.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        for (category in categoriesMap) {
            Spacer(modifier = Modifier.width(4.dp))
            FilterChip(
                selected = homeScreenState.productListCategory == category.key,
                onClick = { onClick(category.key) },
                label = { Text(text = category.value) }
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}