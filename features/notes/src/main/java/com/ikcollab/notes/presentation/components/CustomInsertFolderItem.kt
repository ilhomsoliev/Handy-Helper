package com.ikcollab.notes.presentation.components

import android.view.ViewTreeObserver
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.ikcollab.components.ModalSheetDefaultStick
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomInsertFolderItem(
    value:String,
    onValueChange:(String)->Unit,
    onClick:()->Unit,
    placeholder: String,
    modifier: Modifier,
    stateOfKeyboard:MutableState<Boolean> = mutableStateOf(false)
) {
    val view = LocalView.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }

    val keyboard = LocalSoftwareKeyboardController.current
    if(stateOfKeyboard.value)
    DisposableEffect(view) {
        focusRequester.requestFocus()
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            keyboard?.hide()
        }
    }
    else
    {
        keyboard?.hide()
    }
    Column(modifier = modifier) {
        Column(modifier = Modifier
            .bringIntoViewRequester(bringIntoViewRequester)
        ) {
            ModalSheetDefaultStick()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomInsertFolderTextField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = placeholder,
                    focusRequester = focusRequester,
                    keyboard = keyboard
                )
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.secondary,
                    onClick = {
                        if (keyboard != null) {
                            keyboard.hide()
                        }
                        focusRequester.freeFocus()
                        stateOfKeyboard.value = false
                        onClick()
                              },
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null)
                }
            }
        }
    }
}