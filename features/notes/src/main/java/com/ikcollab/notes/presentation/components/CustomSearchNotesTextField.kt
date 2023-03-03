package com.ikcollab.notes.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSearchNotesTextField(
    value:String,
    onValueChange:(String)->Unit,
    placeholder:String,
    width:Float = 0.85f,
    height:Int = 50,
    paddingEnd:Int = 0,
    paddingStart:Int = 0
) {
    TextField(
    modifier= Modifier
    .fillMaxWidth(width)
    .padding(start = paddingStart.dp, end = paddingEnd.dp)
    .height(height = height.dp),
    value = value,
    placeholder = { Text(text = placeholder, color = Color.Gray, fontSize = 14.sp) },
    onValueChange =  onValueChange,
    textStyle = TextStyle.Default.copy(fontSize = 14.sp, fontWeight = FontWeight.Black),
    colors = TextFieldDefaults.textFieldColors(
    textColor = MaterialTheme.colors.onBackground,
    disabledTextColor = Color.Transparent,
    backgroundColor = MaterialTheme.colors.primary,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    ),
    shape = RoundedCornerShape(15.dp),
    leadingIcon = {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
    },
    trailingIcon = {
        if(value!="") {
            IconButton(onClick = { onValueChange("") }) {
                Icon(imageVector = Icons.Default.Close, contentDescription =null )
            }
        }
    },
    singleLine = true,
    maxLines = 1
    )
}