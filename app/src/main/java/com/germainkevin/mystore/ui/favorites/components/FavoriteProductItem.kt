package com.germainkevin.mystore.ui.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.germainkevin.mystore.data.Product
import com.germainkevin.mystore.R

@Composable
fun FavoriteProductItem(
    modifier: Modifier = Modifier,
    product: Product,
) {
    Card(
        modifier = modifier,
        elevation = 6.dp,
    ) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                model = ImageRequest.Builder(LocalContext.current).data(product.image).build(),
                placeholder = painterResource(id = R.drawable.ic_baseline_image_24),
                contentDescription = stringResource(id = R.string.product_image),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${product.price} $",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
            Text(
                text = product.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }
}