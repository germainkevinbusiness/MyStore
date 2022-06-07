package com.germainkevin.mystore.ui.cart.components

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
import com.germainkevin.mystore.R
import com.germainkevin.mystore.data.Product

@Composable
fun CartTopAppBar(
    itemsInCart: List<Product>,
    navActions: NavActions,
    openLeftDrawer: () -> Unit,
    scrollBehavior: CollapsingTopBarScrollBehavior
) {

    CollapsingTopBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = R.string.cart),
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        subtitle = {
            Text(
                text = stringResource(id = R.string.product_count, itemsInCart.size.toString()),
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