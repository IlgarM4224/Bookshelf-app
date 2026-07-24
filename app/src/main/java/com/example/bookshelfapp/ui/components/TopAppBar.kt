package com.example.bookshelfapp.ui.components

import androidx.compose.foundation.layout.Row
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

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.app_name),
    onBackClick: () -> Unit = {},
    canNavigateBack: Boolean = true
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if(canNavigateBack) {
            IconButton( onClick = onBackClick ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }

        Text(
            text = label,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
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
            canNavigateBack = true
        )
    }
}