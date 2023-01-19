@file:OptIn(ExperimentalMaterialApi::class)

package com.ikcollab.notes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.notes.presentation.components.CustomNotesCategory
import com.ikcollab.notes.presentation.theme.WhiteRed
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
   openNoteDetails:()->Unit,
   viewModel: NotesScreenViewModel = hiltViewModel()
) {
   val stateNumberCategoriesNote by remember {
      viewModel.stateNumberCategoriesNote
   }
   val coroutineScope = rememberCoroutineScope()
   val bottomSheetScaffoldState = rememberModalBottomSheetState(
      initialValue = ModalBottomSheetValue.Hidden,
      confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
   )
   val listOfCategory = listOf("Diary","Success diary","Thanks","My mistakes","Diary of happiness")
   ModalBottomSheetLayout(
      sheetState = bottomSheetScaffoldState,
      sheetContent = {
      Column(modifier = Modifier
         .fillMaxSize()
         .background(WhiteRed)) {
         Button(onClick = { coroutineScope.launch {
            bottomSheetScaffoldState.hide()
         } }) {
         }
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
   }){

   }
}