package com.ikcollab.notes.presentation.showDetailsOfNoteScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.core.Constants
import java.sql.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShowDetailsOfNoteScreen() {
    Scaffold() { _ ->
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                text = Constants.NOTE_TITLE, fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = if (Constants.FOLDER_NAME.value == "FOLDER_NAME") "" else Constants.FOLDER_NAME.value)
                Text(text = Date(Constants.NOTE_DATE_TIME).toString())
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp), text = Constants.NOTE_DESCRIPTION
            )
        }
    }
}