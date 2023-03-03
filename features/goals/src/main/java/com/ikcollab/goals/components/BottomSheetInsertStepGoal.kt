package com.ikcollab.goals.components

import android.os.Build
import android.text.format.DateUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import com.ikcollab.components.DatePickerLabel
import com.ikcollab.components.ModalSheetDefaultStick
import com.ikcollab.components.ModalSheetTextField
import com.ikcollab.components.SendIcon
import com.ikcollab.core.toMMDDYYYY
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomSheetInsertStepGoal(
    stepGoalValue: String,
    onStepGoalValueChange: (String) -> Unit,
    deadline: Long,
    onDeadlineChange: (Long) -> Unit,
    onAddClick: () -> Unit
) {
    val dateDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxWidth()

            .background(Color(0xFF34568B)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModalSheetDefaultStick(
            modifier = Modifier.padding(top = 16.dp)
        )
        ModalSheetTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = stepGoalValue,
            hint = "Add a new step",
            onValueChange = onStepGoalValueChange
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DatePickerLabel(date = deadline.toMMDDYYYY(), onClick = {
                dateDialogState.show()
            })
            SendIcon(
                modifier = Modifier
                    .focusTarget(),
                onClick = onAddClick
            )
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {

            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.ofEpochDay(deadline),
            title = "Pick a date"
        ) {
            onDeadlineChange(it.toEpochDay())
        }
    }
}