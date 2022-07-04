package com.germainkevin.mystore.ui.settings.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarScrollBehavior
import com.germainkevin.mystore.R
import com.germainkevin.mystore.utils.NavActions

@Composable
fun SettingsTopBar(
    navActions: NavActions,
    scrollBehavior: CollapsingTopBarScrollBehavior,
) {
    CollapsingTopBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = R.string.action_settings),
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
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