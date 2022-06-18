package com.germainkevin.mystore.ui.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.germainkevin.mystore.MainActivity
import com.germainkevin.mystore.R
import com.germainkevin.mystore.ui.about.components.AboutTopBar
import com.germainkevin.mystore.ui.theme.WebLinkColor
import com.germainkevin.mystore.utils.NavActions
import com.germainkevin.mystore.utils.sendUserToSpecificWebPage

@Composable
fun AboutScreen(navActions: NavActions, activity: MainActivity) {
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(centeredTitleAndSubtitle = false)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = rememberScaffoldState(),
        topBar = { AboutTopBar(navActions = navActions, scrollBehavior = scrollBehavior) },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
        ) {
            val annotatedText = buildAnnotatedString {
                append(stringResource(id = R.string.about_my_store_content_prefix))

                // We attach this *URL* annotation to the following content
                // until `pop()` is called
                pushStringAnnotation(
                    tag = "URL",
                    annotation = stringResource(id = R.string.about_my_store_link)
                )
                withStyle(
                    style = SpanStyle(
                        color = WebLinkColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(" https://fakestoreapi.com ")
                }

                append(stringResource(id = R.string.about_my_store_content_suffix))

                pop()
            }

            ClickableText(
                text = annotatedText,
                style = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = { offset ->
                    // We check if there is an *URL* annotation attached to the text
                    // at the clicked position
                    annotatedText.getStringAnnotations(
                        tag = "URL", start = offset,
                        end = offset
                    )
                        .firstOrNull()?.let { annotation ->
                            sendUserToSpecificWebPage(annotation.item, activity)
                        }
                }
            )
        }
    }
}