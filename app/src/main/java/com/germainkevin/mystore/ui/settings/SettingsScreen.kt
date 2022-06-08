package com.germainkevin.mystore.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.germainkevin.mystore.NavActions
import com.germainkevin.mystore.ui.settings.components.SettingsTopBar

@Composable
fun SettingsScreen(navActions: NavActions) {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(centeredTitleAndSubtitle = false)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = rememberScaffoldState(),
        topBar = { SettingsTopBar(navActions = navActions, scrollBehavior = scrollBehavior) },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
        ) {}
    }
}