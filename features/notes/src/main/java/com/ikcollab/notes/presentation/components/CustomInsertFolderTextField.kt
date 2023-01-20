package com.ikcollab.notes.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CustomInsertFolderTextField(
    value:String,
    onValueChange:(String)->Unit,
    placeholder:String,
    focus:MutableState<Boolean>
) {
//    val inputService = LocalTextInputService.current
//    val scope = rememberCoroutineScope()
//    scope.launch{
//        if(focus.value){
//            inputService?.showSoftwareKeyboard()
//        }
//        else {
//            inputService?.hideSoftwareKeyboard()
//        }
//    }
    TextField(
        modifier=Modifier.fillMaxWidth(0.8f).padding(start = 15.dp),
        value = value,
        placeholder = { Text(text = placeholder) },
        onValueChange =  onValueChange,
        textStyle = TextStyle.Default.copy(fontSize = 20.sp, fontWeight = FontWeight.Black),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(5.dp)
    )
}