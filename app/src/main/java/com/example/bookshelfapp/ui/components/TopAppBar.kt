package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelfapp.R
import com.example.bookshelfapp.model.Genre
import com.example.bookshelfapp.ui.ContentType
import com.example.bookshelfapp.ui.CurrentScreen

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    currentScreen: CurrentScreen = CurrentScreen.Genre,
    selectedGenre: Genre? = null,
    contentType: ContentType = ContentType.OnlyInfo,
    onBackClick: (CurrentScreen) -> Unit = {},
){
    val label = when (currentScreen) {
        CurrentScreen.Books -> selectedGenre?.displayName ?: stringResource(R.string.app_name)
        CurrentScreen.Info -> CurrentScreen.Info.name
        CurrentScreen.Loading -> stringResource(R.string.loading_top_bar)
        CurrentScreen.Error -> stringResource(R.string.if_null)
        else -> stringResource(R.string.app_name)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(currentScreen != CurrentScreen.Genre) {
            IconButton( onClick = { onBackClick(currentScreen) } ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }

        if (contentType == ContentType.BooksAndInfo && currentScreen == CurrentScreen.Info) {
            Text(
                text =  selectedGenre?.displayName ?: stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            )

            Spacer(Modifier.weight(1f))
        }

        Text(
            text = label,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        )

        Spacer(Modifier.padding(dimensionResource(R.dimen.padding_small)))
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
            currentScreen = CurrentScreen.Info,
            selectedGenre = Genre.Horror,
            contentType = ContentType.BooksAndInfo
        )
    }
}