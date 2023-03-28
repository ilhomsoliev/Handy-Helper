package com.ikcollab.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.components.datePicker.DatePicker
import com.ikcollab.core.toCurrentInMillis
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import java.time.ZoneId
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModalSheetDatePicker(
    modifier: Modifier,
    value: String,
    title: String,
    onValueChange: (Long) -> Unit
) {
    val calendarState = rememberSheetState()
    DatePicker(calendarState) {
        onValueChange(it.toCurrentInMillis())
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.primary)
            .clickable {
                calendarState.show()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.onPrimary
            )
            Row(modifier = Modifier.clickable {
                calendarState.show()
            }, verticalAlignment = Alignment.CenterVertically) {
                Text(text = value, color = MaterialTheme.colors.onPrimary)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }
    }
}