package com.ikcollab.notes.presentation.addNoteScreen

import android.annotation.SuppressLint
import android.util.Log
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
import com.ikcollab.components.CustomDropDownMenu
import com.ikcollab.components.DatePickerLabel
import com.ikcollab.core.Constants
import com.ikcollab.core.Constants.FOLDER_ID_IS_NULL
import com.ikcollab.core.Constants.WHICH_NOTE
import com.ikcollab.model.dto.note.FolderDto
import com.ikcollab.notes.presentation.components.CustomInsertFolderTextField
import com.ikcollab.notes.presentation.components.DatePicker
import com.ikcollab.notes.presentation.notesScreen.NotesScreenViewModel
import com.ikcollab.notes.presentation.theme.WhiteRed
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime

@SuppressLint("InvalidColorHexValue", "CoroutineCreationDuringComposition", "NewApi")
@Composable
fun AddNoteScreen(
    state: AddNoteScreenState,
    onEvent: (AddNoteScreenEvent) -> Unit
) {
    val folderIdNull = remember { mutableStateOf(-1) }
    var stateTitleNotNull by remember {
        mutableStateOf(false)
    }
    val calendarState = rememberSheetState()
    val notesScreenViewModel: NotesScreenViewModel = hiltViewModel()
    val selectedCategory = remember { mutableStateOf("Choose a folder") }
    val coroutineScope = rememberCoroutineScope()

    state.note?.let { note ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            CustomInsertFolderTextField(
                value = note.title,
                onValueChange = { onEvent(AddNoteScreenEvent.OnTitleChange(it)) },
                placeholder = "Title",
                width = 1f,
                paddingEnd = 15
            )
            Spacer(modifier = Modifier.height(12.dp))

            CustomInsertFolderTextField(
                value = note.description,
                onValueChange = { onEvent(AddNoteScreenEvent.OnDescriptionChange(it)) },
                placeholder = "Description..",
                width = 1f,
                height = 100,
                paddingEnd = 15
            )

            Spacer(modifier = Modifier.height(25.dp))

            if (FOLDER_ID_IS_NULL.value || Constants.WHICH_NOTE.value == Constants.EDIT_NOTE) {
                LaunchedEffect(key1 = true) {
                    notesScreenViewModel.getFolders()
                }
                CustomDropDownMenu(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                    suggestions = notesScreenViewModel.stateFolder.value.folders.map { it.name },
                    selectedText = selectedCategory.value,
                    onClick = { categoryName ->
                        val response: FolderDto? =
                            notesScreenViewModel.stateFolder.value.folders.find {
                                it.name == categoryName
                            }
                        selectedCategory.value = categoryName
                        folderIdNull.value = response?.id ?: -1
                        Log.e("FOLDERIDNULL", "${folderIdNull.value}")
                    })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Choose a date",
                    color = Color.Gray
                )
                /* DatePickerLabel(date = stateNoteDate.value) {
                     calendarState.show()
                 }*/
            }

            /*    DatePicker(calendarState) {
                    viewModel.updateNoteDate(it.toString())
                }*/
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 25.dp, bottom = 25.dp), contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(backgroundColor = Color.Red, onClick = {
                    onEvent(AddNoteScreenEvent.InsertToDatabase)
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
                Text(
                    text = "Required field",
                    fontSize = 18.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
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
}