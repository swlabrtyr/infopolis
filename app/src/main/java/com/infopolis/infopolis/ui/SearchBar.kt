package com.infopolis.infopolis.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.infopolis.infopolis.data.model.CitiesListViewModel

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    input: String,
    viewModel: CitiesListViewModel
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = "Search icon",
            modifier = Modifier
                .width(50.dp)
                .height(20.dp)
                .align(Alignment.CenterVertically),
            tint = Color.White,
        )

        var hint by rememberSaveable { mutableStateOf("") }

        Box(modifier = modifier) {
            BasicTextField(
                value = input,
                onValueChange = viewModel::searchCities,
                maxLines = 1,
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        hint = if (it.isFocused) {
                            ""
                        } else {
                            "Search.."
                        }
                    }
                    .padding(vertical = 12.dp)
            )

            if (input.isEmpty()) {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}