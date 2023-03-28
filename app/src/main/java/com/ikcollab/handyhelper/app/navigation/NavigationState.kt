package com.ikcollab.handyhelper.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class NavigationState constructor(
    val folderName: String = "",
    val folderId: Int = -1,
    val todoCategoryName: String = "",
    val goalName: String = "",
    val goalStartDate: Long = System.currentTimeMillis(),
    val goalEndDate: Long = System.currentTimeMillis(),
    val searchState: String = "",
    val todoTaskName: String = "",
    val todoTaskDeadline: Long = System.currentTimeMillis(),
    val todoCategoryId: Int = -1,
)