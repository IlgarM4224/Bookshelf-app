package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BooksResponse
import com.example.bookshelfapp.ui.components.TopAppBar
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme


@Composable
fun BookshelfAppContent(
    modifier: Modifier = Modifier,
    genres: List<String> = emptyList(),
    bookshelfResponse: BooksResponse = BooksResponse()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                genres = genres,
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Text (
            text = "test",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun BookshelfAppContentPreview(){
    BookshelfAppTheme {
        BookshelfAppContent(modifier = Modifier.fillMaxSize())
    }
}