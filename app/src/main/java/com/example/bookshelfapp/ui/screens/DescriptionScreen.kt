package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.VolumeInfo
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun BookInfo (
    modifier: Modifier = Modifier,
    volumeInfo: VolumeInfo
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
            BooksImage(
                imageLink = volumeInfo.imageLinks?.secureThumbnail,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding)
            )

            Spacer(modifier = Modifier.height(mediumPadding))

            Description(
                title = volumeInfo.title,
                authors = volumeInfo.authors,
                publisher = volumeInfo.publisher,
                publishedDate = volumeInfo.publishedDate,
                pageCount = volumeInfo.pageCount,
                categories = volumeInfo.categories,
                language = volumeInfo.language,
                description = volumeInfo.description,
                modifier = Modifier.padding(smallPadding)
            )
        }
    }
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    title: String?,
    authors: List<String>?,
    publisher: String?,
    publishedDate: String?,
    pageCount: Int?,
    categories: List<String>?,
    language: String?,
    description: String?

) {
    val mediumPadding = dimensionResource(R.dimen.padding_small)
    val smallPadding = dimensionResource(R.dimen.padding_small)/2
    val nullString = stringResource(R.string.if_null)

    Card{
        Column(modifier = modifier) {
            Text(
                text = "Title: ${title ?: nullString}",
                style = MaterialTheme.typography.headlineSmall,
            )

            Spacer(Modifier.padding(mediumPadding))

            Text(
                text = "Authors: ${authors?.joinToString() ?: nullString}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.padding(smallPadding))

            Text(
                text = "Publisher: ${publisher ?: nullString}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.padding(smallPadding))

            Text(
                text = "Publisher date: ${publishedDate ?: nullString}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.padding(smallPadding))

            Text(
                text = "Page count: ${pageCount ?: nullString}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.padding(smallPadding))

            Text(
                text = "Categories: ${categories?.joinToString() ?: nullString}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.padding(smallPadding))

            Text(
                text = "Language: ${language ?: nullString}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.padding(smallPadding))

            Text(
                text = "Description: ${description ?: nullString}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
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
        )
    }
}

@Preview
@Composable
fun BookInfoPreview(){
    BookshelfAppTheme{
        Surface {
            BookInfo(volumeInfo = test)
        }
    }
}

@Preview
@Composable
fun BookInfoDarkPreview(){
    BookshelfAppTheme(darkTheme = true) {
        Surface {
            BookInfo(volumeInfo = test)
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