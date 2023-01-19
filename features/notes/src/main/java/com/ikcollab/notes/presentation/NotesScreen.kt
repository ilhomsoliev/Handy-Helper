package com.ikcollab.notes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.notes.presentation.components.CustomNotesCategory
import com.ikcollab.notes.presentation.theme.WhiteRed

@Composable
fun NotesScreen(
   openNoteDetails:()->Unit,
   viewModel: NotesScreenViewModel = hiltViewModel()
) {
   val stateNumberCategoriesNote by remember {
      viewModel.stateNumberCategoriesNote
   }
   val listOfCategory = listOf("Diary","Success diary","Thanks","My mistakes","Diary of happiness")
   Column(modifier=Modifier.fillMaxSize().background(WhiteRed)) {
      LazyColumn(
         modifier = Modifier
            .padding(top = 10.dp)
      ) {
         items(listOfCategory) { category ->
            CustomNotesCategory(
               onClick = { /*TODO*/ },
               icon = Icons.Default.Folder,
               title = category,
               number = stateNumberCategoriesNote
            )
            Spacer(modifier = Modifier.height(10.dp))
         }
      }
   }
}