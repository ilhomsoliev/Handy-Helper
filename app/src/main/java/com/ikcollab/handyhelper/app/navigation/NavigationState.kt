package com.ikcollab.handyhelper.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class NavigationState constructor(
    val folderName: String = "",
    val todoCategoryName: String = "",
    val goalName: String = "",
    val goalStartDate: Long = System.currentTimeMillis(),
    val goalEndDate: Long = System.currentTimeMillis(),
    val stepGoalId: Int = -1,
    val stepGoalName: String = "",
    val stepGoalDeadline: Long = LocalDate.ofEpochDay(LocalDate.now().toEpochDay()).toEpochDay(),
    val todoTaskName: String = "",
    val todoTaskDeadline: Long = System.currentTimeMillis(),
    val todoCategoryId: Int = -1,
)