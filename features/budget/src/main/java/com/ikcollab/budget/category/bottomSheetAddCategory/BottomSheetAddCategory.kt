package com.ikcollab.budget.category.bottomSheetAddCategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.components.ModalSheetDefaultStick
import com.ikcollab.components.ModalSheetTextField
import com.ikcollab.components.SendIcon

@Composable
fun BottomSheetAddCategory(
    state: BottomSheetAddCategoryState,
    onEvent: (BottomSheetAddCategoryEvent) -> Unit,
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
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            ModalSheetTextField(
                modifier = Modifier
                    .weight(90f)
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                value = state.categoryName,
                hint = "Enter a goal",
                onValueChange = {
                    onEvent(BottomSheetAddCategoryEvent.OnCategoryNameChange(it))
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            SendIcon(
                modifier = Modifier
                    .weight(10f)
                    .focusTarget(),
                onClick = {
                    onEvent(BottomSheetAddCategoryEvent.OnAddClick)
                }
            )
        }
    }
}