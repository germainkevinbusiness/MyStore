package com.germainkevin.mystore.ui.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 4.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        androidx.compose.material3.FilterChip(
            selected = homeScreenState.value.productListCategory == ProductListCategory.AllCategories,
            onClick = {
                homeViewModel.getProducts(productListCategory = ProductListCategory.AllCategories)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.all_categories),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Spacer(modifier = Modifier.width(4.dp))
        androidx.compose.material3.FilterChip(
            selected = homeScreenState.value.productListCategory == ProductListCategory.Electronics,
            onClick = {
                homeViewModel.getProducts(productListCategory = ProductListCategory.Electronics)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.electronics),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Spacer(modifier = Modifier.width(4.dp))
        androidx.compose.material3.FilterChip(
            selected = homeScreenState.value.productListCategory == ProductListCategory.Jewelery,
            onClick = {
                homeViewModel.getProducts(productListCategory = ProductListCategory.Jewelery)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.jewelery),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Spacer(modifier = Modifier.width(4.dp))
        androidx.compose.material3.FilterChip(
            selected = homeScreenState.value.productListCategory == ProductListCategory.MenClothing,
            onClick = {
                homeViewModel.getProducts(productListCategory = ProductListCategory.MenClothing)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.men_clothing),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Spacer(modifier = Modifier.width(4.dp))
        androidx.compose.material3.FilterChip(
            selected = homeScreenState.value.productListCategory == ProductListCategory.WomenClothing,
            onClick = {
                homeViewModel.getProducts(productListCategory = ProductListCategory.WomenClothing)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.women_clothing),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Spacer(modifier = Modifier.width(4.dp))
    }
}