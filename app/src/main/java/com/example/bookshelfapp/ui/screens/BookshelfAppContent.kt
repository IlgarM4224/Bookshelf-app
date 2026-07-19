package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.BookItem
import com.example.bookshelfapp.model.BooksResponse
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.ui.components.TopAppBar
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme


@Composable
fun BookshelfAppContent(
    modifier: Modifier = Modifier,
    genres: List<Genre> = emptyList(),
    onGenreClick: (Genre) -> Unit = {},
    bookshelfResponse: BooksResponse = BooksResponse()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                genres = genres,
                onGenreClick = onGenreClick,
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        },
        modifier = modifier
    ) { innerPadding ->
        BookCoversGrid(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            books = bookshelfResponse.items
        )
    }
}

@Composable
fun BookCoversGrid(
    modifier: Modifier = Modifier,
    books: List<BookItem>?
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        if (books?.size != null){
            items(books.size){
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(books[it].volumeInfo?.imageLinks?.secureThumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun BookshelfAppContentPreview(){
    BookshelfAppTheme {
        BookshelfAppContent(modifier = Modifier.fillMaxSize() )
    }
}