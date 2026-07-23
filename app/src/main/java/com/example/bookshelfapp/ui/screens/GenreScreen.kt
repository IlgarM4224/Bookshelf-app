package com.example.bookshelfapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.model.GenreProvider
import com.example.bookshelfapp.ui.components.TopAppBar
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun GenreScreen(
    modifier: Modifier = Modifier,
    genres: List<Genre> = GenreProvider.getGenres(),
    onClick: (Genre) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                canNavigateBack = false
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = dimensionResource(R.dimen.vertical_grid_min_size)),
            modifier = modifier.padding(innerPadding),
        ) {
            items(genres.size){
                GenreCard(
                    title = genres[it].displayName,
                    imageRes = genres[it].iconId,
                    onClick = { onClick(genres[it]) },
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
    }
}

@Composable
fun GenreCard(
    title: String,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val miniPadding = dimensionResource(R.dimen.padding_mini)
    val smallPadding = dimensionResource(R.dimen.padding_small)
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(miniPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.genre_icon_size))
                    .padding(smallPadding)

            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(miniPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenreScreenPreview() {
    BookshelfAppTheme {
        Surface {
            GenreScreen(
                genres = GenreProvider.getGenres(),
                onClick = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}
