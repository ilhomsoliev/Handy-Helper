@file:OptIn(ExperimentalMaterialApi::class)

package com.ikcollab.notes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.*
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
   val coroutineScope = rememberCoroutineScope()

   val stateFolder = viewModel.stateFolder.value.folders
   Column(
      modifier = Modifier
         .fillMaxSize()
         .background(WhiteRed)
   ) {
      LazyColumn(
         modifier = Modifier
            .padding(top = 10.dp)
      ) {
         items(stateFolder) { folder ->
            CustomNotesCategory(
               onClick = { /*TODO*/ },
               icon = Icons.Default.Folder,
               title = folder.name,
               number = stateNumberCategoriesNote
            )
            Spacer(modifier = Modifier.height(10.dp))
         }
      }
   }
}