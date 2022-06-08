package com.germainkevin.mystore.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.germainkevin.mystore.R
import com.germainkevin.mystore.data.Product

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    onProductClick: () -> Unit,
    onAddToCart: (Boolean) -> Unit,
    onAddToFavorites: (Boolean) -> Unit,
) {
    Card(
        modifier = modifier.clickable { onProductClick() },
        elevation = 6.dp,
    ) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
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
                    maxLines = 2,
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
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.AddShoppingCart,
                                contentDescription = "Add Shopping Cart icon",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(end = 4.dp)
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
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.TopEnd),
                onClick = { onAddToFavorites(!product.addedAsFavorite) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                if (product.addedAsFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(id = R.string.added_to_favorites) + " icon",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.add_to_favorites) + " icon",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
    }
}