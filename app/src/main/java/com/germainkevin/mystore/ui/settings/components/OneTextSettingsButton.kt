package com.germainkevin.mystore.ui.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @param title The name of the Settings Action
 * @param triggersActionOnClick Should [doOnClick] be called when a click event is recorded on
 * this composable
 * @param doOnClick A callback method for when a click event is recorded
 * */
@Composable
fun OneTextSettingsButton(
    title: String,
    triggersActionOnClick: Boolean,
    hasDivider: Boolean = true,
    doOnClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { if (triggersActionOnClick) doOnClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                color =
                if (triggersActionOnClick) MaterialTheme.colorScheme.onSurface
                else Color.Gray
            )
        }
        if (hasDivider) Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    }
}