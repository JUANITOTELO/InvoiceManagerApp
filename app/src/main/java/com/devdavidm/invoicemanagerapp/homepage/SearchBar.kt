package com.devdavidm.invoicemanagerapp.homepage

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(){
    val text = remember { mutableStateOf(TextFieldValue()) }

    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, Color.Black, RoundedCornerShape(20.dp)),
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier
                    .height(35.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color(0xFF282828),
            focusedTextColor = Color.Black,
            unfocusedPlaceholderColor = Color.Black,
            focusedPlaceholderColor = Color.Black,
            unfocusedContainerColor = Color(0xFFFAFAFA),
            focusedContainerColor = Color(0xFFF0F0F0),
            unfocusedLeadingIconColor = Color(0xFF282828),
            focusedLeadingIconColor = Color.Black,
            unfocusedTrailingIconColor = Color(0xFF282828),
            focusedTrailingIconColor = Color.Black,
            cursorColor = Color.Black

        )
    )
}