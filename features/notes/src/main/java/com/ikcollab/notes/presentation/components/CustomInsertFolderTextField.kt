package com.ikcollab.notes.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CustomInsertFolderTextField(
    value:String,
    onValueChange:(String)->Unit,
    placeholder:String,
    width:Float = 0.8f,
    height:Int = 55,
    paddingEnd:Int = 0,
    paddingStart:Int = 15,
    keyboard:SoftwareKeyboardController?=null,
    focusRequester:FocusRequester
) {
    TextField(
        modifier= Modifier
            .fillMaxWidth(width)
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    keyboard?.show()
                }
            }
            .padding(start = paddingStart.dp, end = paddingEnd.dp)
            .height(height = height.dp),
        value = value,
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        onValueChange =  onValueChange,
        textStyle = TextStyle.Default.copy(fontSize = 20.sp, fontWeight = FontWeight.Black),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            disabledTextColor = Color.Transparent,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(5.dp),
        singleLine = true,
        maxLines = 1
    )
}