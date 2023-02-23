package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    input: String,
    onChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SearchBar(
            input = input,
            onChange = onChange,
        )
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        modifier = Modifier,
        input = "Dallas",
        onChange = {})
}

