package com.notes.notes.presentation.notes_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalFoundationApi
@Composable
fun NotesScreen(
    navController: NavController,
//    viewModel: NotesScreenViewModel = hiltViewModel(),
) {
    var search by remember { mutableStateOf("") }
    TextField(value =search, onValueChange = {
        search = it
    })
    Text(search)
}