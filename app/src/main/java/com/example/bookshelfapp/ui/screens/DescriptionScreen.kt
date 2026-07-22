package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.VolumeInfo
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun BookInfo (
    modifier: Modifier = Modifier,
    volumeInfo: VolumeInfo?,
    onBackClick: () -> Unit,
) {
    val smallPadding = dimensionResource(R.dimen.padding_small)
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Surface (modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(mediumPadding)
        ){
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null
                )
            }
            BooksImage(
                imageLink = volumeInfo?.imageLinks?.secureThumbnail,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding)
            )

            Spacer(modifier = Modifier.height(mediumPadding))

            Text(
                text = volumeInfo?.title ?: stringResource(R.string.if_null),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            if (!volumeInfo?.authors.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(smallPadding/2))
                Text(
                    text = volumeInfo.authors.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(mediumPadding))

            BookBadges(volumeInfo = volumeInfo)

            Spacer(modifier = Modifier.height(mediumPadding))

            DescriptionCard(volumeInfo = volumeInfo)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookBadges(volumeInfo: VolumeInfo?) {
    val smallPadding = dimensionResource(R.dimen.padding_small)

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(smallPadding, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(smallPadding),
        modifier = Modifier.fillMaxWidth()
    ) {
        volumeInfo?.pageCount?.let {
            AssistChip(
                onClick = {},
                label = { Text("$it pages") }
            )
        }
        volumeInfo?.language?.let {
            AssistChip(
                onClick = {},
                label = { Text("Language: ${it.uppercase()}") }
            )
        }
        volumeInfo?.categories?.forEach { category ->
            AssistChip(
                onClick = {},
                label = { Text(category) }
            )
        }
    }
}

@Composable
fun DescriptionCard(
    volumeInfo: VolumeInfo?,
    modifier: Modifier = Modifier
) {
    val smallPadding = dimensionResource(R.dimen.padding_small)
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val nullString = stringResource(R.string.if_null)

    Card (
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(mediumPadding)
    ) {
        Column(
            modifier = Modifier.padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(smallPadding)
        ) {
            if (volumeInfo?.publisher != null || volumeInfo?.publishedDate != null) {
                InfoRow(label = "Publisher", value = volumeInfo.publisher ?: nullString)
                InfoRow(label = "Published date", value = volumeInfo.publishedDate ?: nullString)

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = smallPadding/2),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = volumeInfo?.description ?: nullString,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.25f
            )
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
@Composable
fun BooksImage(
    modifier: Modifier,
    imageLink: String? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageLink)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            fallback = painterResource(R.drawable.not_found),
            placeholder = painterResource(R.drawable.loading),
            modifier = Modifier
                .size(
                    width = dimensionResource(R.dimen.description_image_width),
                    height = dimensionResource(R.dimen.description_image_height)
                )
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Preview
@Composable
fun BookInfoPreview(){
    BookshelfAppTheme{
        Surface {
            BookInfo(volumeInfo = test, onBackClick = {})
        }
    }
}

@Preview
@Composable
fun BookInfoDarkPreview(){
    BookshelfAppTheme(darkTheme = true) {
        Surface {
            BookInfo(volumeInfo = test, onBackClick = {})
        }
    }
}


private val test = VolumeInfo(
    title = "How to Draw MORE...Pirates",
    authors = listOf("Ben Dunn"),
    publisher = "Antarctic Press",
    publishedDate = "2009-04-14",
    pageCount = 130,
    categories = listOf("Art"),
    language = "en",
    description = " The ULTIMATE book on \"HOW to DRAW\" the awesome world of PIRATES! Taught with " +
            "easy-to-follow, STEP-by-STEP instructions by top artists, you'll be creating your " +
            "own pirates in no time at all! Prepare to sharpen those pencils and set sail on an adventure of " +
            "artistic plundering!"
)