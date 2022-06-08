package com.germainkevin.mystore.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @param title The name of the Settings Action
 * @param state The state to toggle the [Switch]
 * @param hasDivider Whether to have
 * */
@Composable
fun OneTextToggleSettingsButton(
    title: String,
    state: State<Boolean>,
    hasDivider: Boolean,
    doOnClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { doOnClick() },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            Switch(
                modifier = Modifier.padding(start = 8.dp),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary
                ),
                checked = state.value, onCheckedChange = {
                    doOnClick()
                    // Do not put doOnClick() here, it will do the opposite of the above code
                })
        }
        if (hasDivider) Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}