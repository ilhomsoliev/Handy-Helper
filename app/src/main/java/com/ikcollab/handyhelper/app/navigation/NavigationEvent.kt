package com.ikcollab.handyhelper.app.navigation

sealed class NavigationEvent {

    data class OnNewGoalNameChange(val value: String) : NavigationEvent()
    data class OnNewStepGoalNameChange(val value: String) : NavigationEvent()
    data class OnNewStepGoalIdChange(val value: Int) : NavigationEvent()
    data class OnFolderNameChange(val value: String) : NavigationEvent()
    data class OnFolderIdChange(val value: Int) : NavigationEvent()
    data class OnSearchNotes(val value: String) : NavigationEvent()
    data class OnTodoCategoryNameChange(val value: String) : NavigationEvent()
    data class OnNewGoalStartDateChange(val value: Long) : NavigationEvent()
    data class OnNewStepGoalDeadlineChange(val value: Long) : NavigationEvent()
    data class OnNewGoalEndDateChange(val value: Long) : NavigationEvent()
    object InsertFolder : NavigationEvent()
    object InsertTodoCategory : NavigationEvent()
    object InsertStepGoalToDatabase : NavigationEvent()
    object InsertGoalToDatabase : NavigationEvent()
    object InsertTodoTaskToDatabase : NavigationEvent()
    data class OnTodoCategoryIdChange(val value: Int) : NavigationEvent()
    data class OnTodoNameChange(val value: String) : NavigationEvent()

}