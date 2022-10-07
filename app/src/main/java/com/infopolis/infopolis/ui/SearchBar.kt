package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.infopolis.infopolis.data.model.CitiesListViewModel

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    input: String,
    viewModel: CitiesListViewModel
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = "Search icon",
            modifier = Modifier
                .width(50.dp)
                .height(20.dp)
                .align(Alignment.CenterVertically),
            tint = Color.Black,
        )
        Box(modifier = modifier) {
            BasicTextField(
                value = input,
                onValueChange = viewModel::searchCities,
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            if (input.isEmpty()) {
                Text(
                    text = hint,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(vertical = 12.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}