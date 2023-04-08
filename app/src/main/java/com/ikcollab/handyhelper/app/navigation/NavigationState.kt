package com.ikcollab.handyhelper.app.navigation

data class NavigationState constructor(
    val todoCategoryName: String = "",
    val goalName: String = "",
    val goalStartDate: Long = System.currentTimeMillis(),
    val goalEndDate: Long = System.currentTimeMillis(),
    val searchState: String = "",
    val todoTaskName: String = "",
    val todoTaskDeadline: Long = System.currentTimeMillis(),
    val todoCategoryId: Int = -1,
)