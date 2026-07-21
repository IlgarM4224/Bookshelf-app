package com.example.bookshelfapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.bookshelfapp.R

@Composable
private fun ErrorOrLoading(
    text: String,
    iconId: Int? = null,
    modifier: Modifier
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.padding(dimensionResource(R.dimen.padding_small)))

        if (iconId != null){
            Icon(
                painter = painterResource(iconId),
                "sad emoji",
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
            )
        }
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    errorText: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        ErrorOrLoading(
            text = errorText,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        )

        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen( modifier: Modifier ) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ErrorOrLoading(
            text = stringResource(R.string.loading),
            modifier = Modifier
                .statusBarsPadding()
                .padding(dimensionResource(R.dimen.padding_small))
        )

        CircularProgressIndicator(
            modifier = Modifier
                .width(dimensionResource(R.dimen.progress_width))
                .padding(dimensionResource(R.dimen.padding_small)),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
