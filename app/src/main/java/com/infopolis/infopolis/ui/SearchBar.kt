package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun test_TextSearch(input: String): Unit {}
@Preview
@Composable
fun SearchViewPreview() {
    SearchBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
            .fillMaxWidth(),
        onChange = {test_TextSearch("")},
        input = "Hello"
    )
}
@Composable
fun SearchInput(
    onInput: () -> String,
    onChange: (String) -> Unit,
    hint: (FocusState) -> Unit,
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                hint(it)
            }
            .padding(horizontal = 10.dp),
        value = onInput(),
        onValueChange = onChange,
        maxLines = 1,
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        textStyle = TextStyle(color = Color.White, textAlign = TextAlign.Left),
    )
}
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    input: String,
    onChange: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        var hint by rememberSaveable { mutableStateOf("") }
        Icon(
            Icons.Default.Search,
            contentDescription = "Search icon",
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                ,
            tint = Color.White,
        )

        Box {
            SearchInput(
                onInput = { input },
                onChange = onChange,
                hint = {
                    hint = if (it.isFocused) {
                        ""
                    } else {
                        "Search.."
                    }
                }
            )

            if (input.isEmpty()) {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}