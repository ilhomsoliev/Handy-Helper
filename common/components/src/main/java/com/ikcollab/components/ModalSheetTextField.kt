package com.ikcollab.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ModalSheetTextField(
    modifier:Modifier = Modifier,
    value:String,
    hint:String,
    onValueChange:(String)->Unit
) {
    TextField(
        modifier = modifier.clip(RoundedCornerShape(6.dp)),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = hint)
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}