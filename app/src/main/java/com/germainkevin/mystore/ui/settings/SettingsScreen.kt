package com.germainkevin.mystore.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.germainkevin.mystore.R
import com.germainkevin.mystore.ui.settings.components.OneTextToggleSettingsButton
import com.germainkevin.mystore.ui.settings.components.SettingsTopBar
import com.germainkevin.mystore.utils.NavActions
import com.germainkevin.mystore.utils.PersistentStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    persistentStorage: PersistentStorage,
    dynamicThemeState: MutableState<Boolean>
) {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(centeredTitleAndSubtitle = false)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = rememberScaffoldState(),
        topBar = { SettingsTopBar(navActions = navActions, scrollBehavior = scrollBehavior) },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                OneTextToggleSettingsButton(
                    title = stringResource(id = R.string.use_dynamic_theme),
                    state = dynamicThemeState,
                    hasDivider = true,
                    onCheckedChange = {
                        dynamicThemeState.value = !dynamicThemeState.value
                        coroutineScope.launch(Dispatchers.IO) {
                            persistentStorage.setUsingDynamicThemeState(dynamicThemeState.value)
                        }
                    }
                )
            }
        }
    }
}