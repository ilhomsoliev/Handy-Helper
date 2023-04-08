package com.ikcollab.handyhelper.app.navigation

sealed class NavigationEvent {

    data class OnNewGoalNameChange(val value: String) : NavigationEvent()
    data class OnSearchNotes(val value: String) : NavigationEvent()
    data class OnTodoCategoryNameChange(val value: String) : NavigationEvent()
    object InsertTodoCategory : NavigationEvent()
    object InsertTodoTaskToDatabase : NavigationEvent()
    object StartOverExpenses : NavigationEvent()
    object StartOverIncome : NavigationEvent()
    data class OnTodoCategoryIdChange(val value: Int) : NavigationEvent()
    data class OnTodoNameChange(val value: String) : NavigationEvent()



}