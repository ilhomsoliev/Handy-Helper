package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ikcollab.budget.budget.bottomSheetAddStoryBudget.components.TextFieldAmount
import com.ikcollab.budget.budget.bottomSheetAddStoryBudget.components.TextFieldComment
import com.ikcollab.components.DatePickerLabel
import com.ikcollab.components.ModalSheetDefaultStick
import com.ikcollab.components.SendIcon
import com.ikcollab.core.toMMDDYYYY

@Composable
fun BottomSheetAddStoryBudget(
    state: BottomSheetAddStoryBudgetState,
    onEvent: (BottomSheetAddStoryBudgetEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModalSheetDefaultStick(
            modifier = Modifier.padding(top = 16.dp)
        )
        TextFieldAmount(
            value = if (state.amount == 0) "" else state.amount.toString(),
            onValueChange = {
                onEvent(BottomSheetAddStoryBudgetEvent.OnAmountChange(it))
            }
        )
        TextFieldComment(
            value = state.comment,
            onValueChange = {
                onEvent(BottomSheetAddStoryBudgetEvent.OnCommentChange(it))
            }
        )
        Box(modifier = Modifier.fillMaxWidth()){
            Row(modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.primary)
                .padding(4.dp)
                .clickable {

                }) {
                Text(text = "Choose a category", color = MaterialTheme.colors.onPrimary)
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DatePickerLabel(date = state.date.toMMDDYYYY()) {
                onEvent(BottomSheetAddStoryBudgetEvent.OnDateChange(it))
            }
            SendIcon {

            }
        }

    }

}