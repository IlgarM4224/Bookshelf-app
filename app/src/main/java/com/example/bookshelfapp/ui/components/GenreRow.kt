package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme

@Composable
fun GenreRow(
    modifier: Modifier = Modifier,
    genres: List<Genre> = emptyList(),
    onGenreClick: (Genre) -> Unit
) {
    LazyRow(
        modifier = modifier,
        //contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
    ){
        items(genres.size){ genreIndex ->
            GenreRowItem(
                genre = genres[genreIndex],
                onClick = { onGenreClick(genres[genreIndex]) },
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)/4)
            )
        }
    }
}

@Composable
fun GenreRowItem(
    genre: Genre,
    onClick: (Genre) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        onClick = { onClick(genre) }
    ) {
        Text(
            text = genre.displayName,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun GenreRowPreview(){
    BookshelfAppTheme {
        GenreRow(
            genres = listOf(
                Genre.Dystopian, Genre.Romance, Genre.ScienceFiction, Genre.Horror,
                Genre.History, Genre.Mystery
            ),
            onGenreClick = {},
            modifier = Modifier.statusBarsPadding().fillMaxWidth()
        )
    }
}