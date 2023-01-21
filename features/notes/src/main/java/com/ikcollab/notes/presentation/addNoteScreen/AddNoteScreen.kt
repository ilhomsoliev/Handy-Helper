package com.ikcollab.notes.presentation.addNoteScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.notes.presentation.components.CustomInsertFolderTextField
import com.ikcollab.notes.presentation.notesScreen.NotesScreenViewModel
import com.ikcollab.notes.presentation.theme.WhiteRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("InvalidColorHexValue", "CoroutineCreationDuringComposition")
@Composable
fun AddNoteScreen(
    folderId:Int,
    onGoBack:()->Unit,
    viewModel: AddNoteScreenViewModel = hiltViewModel()
) {
    val stateNoteTitle by viewModel.stateNoteTitle
    val stateNoteDescription by viewModel.stateNoteDescription
    var stateTitleNotNull by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteRed)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        CustomInsertFolderTextField(
            value = stateNoteTitle,
            onValueChange = { viewModel.updateNoteTitle(it) },
            placeholder = "Title",
            width = 1f,
            paddingEnd = 15
        )
        Spacer(modifier = Modifier.height(12.dp))

        CustomInsertFolderTextField(
            value = stateNoteDescription,
            onValueChange = { viewModel.updateNoteDescription(it) },
            placeholder = "Description..",
            width = 1f,
            height = 100,
            paddingEnd = 15
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 25.dp, bottom = 25.dp), contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(backgroundColor = Color.Red, onClick = {
                if (stateNoteTitle != "")
                    viewModel.insertNoteToDatabase(folderId = folderId, onDone = onGoBack)
                else {
                    stateTitleNotNull = true
                }
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
            }
        }
    }

    AnimatedVisibility(
        visible = stateTitleNotNull,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 400, easing = FastOutLinearInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { 250 },
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF87E77))
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Required field", fontSize = 18.sp, color = Color.Red, fontWeight = FontWeight.Bold)
            Text(text = "Title can't be empty", color = Color.Red)
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
    coroutineScope.launch {
        if (stateTitleNotNull) {
            delay(3000)
            stateTitleNotNull = false
        }
    }
}