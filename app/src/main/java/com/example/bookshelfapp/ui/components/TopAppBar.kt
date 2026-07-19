package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Genre

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    genres: List<Genre> = emptyList(),
    onGenreClick: (Genre) -> Unit = {}
){
    Column (modifier = modifier) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        )

        GenreRow(
            genres = genres,
            onGenreClick = onGenreClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview(){
    MaterialTheme {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            genres = listOf(
                Genre.Dystopian, Genre.Romance, Genre.ScienceFiction, Genre.Horror,
                Genre.History, Genre.Mystery
            )
        )
    }
}