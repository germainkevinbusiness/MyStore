package com.germainkevin.mystore.ui.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
fun DetailItem(
    modifier: Modifier = Modifier,
    product: Product,
    onAddToCart: (Boolean) -> Unit,
    onAddToFavorites: (Boolean) -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = 6.dp,
    ) {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
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
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                OutlinedButton(
                    onClick = { onAddToCart(!product.addedToCart) },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(10)
                ) {
                    if (product.addedToCart) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Checked icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text =
                        if (product.addedToCart) stringResource(id = R.string.added_to_cart)
                        else stringResource(
                            id = R.string.add_to_cart
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
                OutlinedButton(
                    onClick = { onAddToFavorites(!product.addedAsFavorite) },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(10)
                ) {
                    if (product.addedAsFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    Text(
                        text =
                        if (product.addedToCart) stringResource(id = R.string.added_to_favorites)
                        else stringResource(
                            id = R.string.add_to_favorites
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}