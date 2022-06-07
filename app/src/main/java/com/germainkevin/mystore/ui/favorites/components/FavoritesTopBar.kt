package com.germainkevin.mystore.ui.favorites.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarScrollBehavior
import com.germainkevin.mystore.NavActions
import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.R

@Composable
fun FavoritesTopAppBar(
    favoriteItems: List<Product>,
    navActions: NavActions,
    openLeftDrawer: () -> Unit,
    scrollBehavior: CollapsingTopBarScrollBehavior
) {

    CollapsingTopBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = R.string.favorites),
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        subtitle = {
            Text(
                text = stringResource(id = R.string.favorite_count, favoriteItems.size.toString()),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = openLeftDrawer) {
                Icon(
                    Icons.Outlined.Menu,
                    contentDescription = stringResource(id = R.string.open_left_drawer_desc),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
    )
}