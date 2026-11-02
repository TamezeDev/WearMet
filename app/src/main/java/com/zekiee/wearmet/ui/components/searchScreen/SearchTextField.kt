package com.zekiee.wearmet.ui.components.searchScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.R
import com.zekiee.wearmet.ui.theme.UnfocusedBgColor

@Composable
fun SearchTextField(searchText: String, onValueChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        value = searchText,
        onValueChange = onValueChange,
        placeholder = { Text(stringResource(R.string.search_hint)) },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = UnfocusedBgColor,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}