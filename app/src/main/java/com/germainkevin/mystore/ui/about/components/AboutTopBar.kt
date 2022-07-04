package com.germainkevin.mystore.ui.about.components

import android.view.Window
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarDefaults
import com.germainkevin.collapsingtopbar.CollapsingTopBarScrollBehavior
import com.germainkevin.mystore.R
import com.germainkevin.mystore.utils.NavActions

@Composable
fun AboutTopBar(
    navActions: NavActions,
    scrollBehavior: CollapsingTopBarScrollBehavior,
    window: Window
) {
    CollapsingTopBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = R.string.about_my_store),
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = CollapsingTopBarDefaults.colors(
            backgroundColorWhenNotCollapsedOrExpanded = MaterialTheme.colorScheme.onPrimaryContainer,
            onBackgroundColorChange = {
                window.statusBarColor = it.toArgb()
            }
        ),
        navigationIcon = {
            IconButton(onClick = { navActions.navController.navigateUp() }) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back_button_desc),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
    )
}