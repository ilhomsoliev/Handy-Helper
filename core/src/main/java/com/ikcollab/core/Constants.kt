package com.ikcollab.core

import androidx.compose.runtime.mutableStateOf

object Constants {
    // TO-DO List Priorities
    const val TO_DO_LIST_PRIORITY_IMPORTANT = "Important"
    const val TO_DO_LIST_PRIORITY_URGENT = "Urgent"
    const val TO_DO_LIST_PRIORITY_LONG_TERM = "Long-term"
    const val TO_DO_LIST_PRIORITY_NO_PRIORITY = "No priority"
    // Repeat Property
    const val REPEAT_DAILY = "Daily"
    const val REPEAT_WEEKLY = "Weekly"
    const val REPEAT_MONTHLY = "Monthly"
    const val REPEAT_DO_NOT_REPEAT = "Do not repeat"
    // Budget Category Type
    const val BUDGET_CATEGORY_EXPENSE = "Expense"
    const val BUDGET_CATEGORY_INCOME = "Income"


    //Category of notes by folderName
    var FOLDER_NAME = mutableStateOf("FOLDER_NAME")
    //Note Details
    var NOTE_TITLE = "NOTE_TITLE"
    var NOTE_DESCRIPTION = "NOTE_DESCRIPTION"
    var NOTE_DATE_TIME:Long = 0

    // ID ARGS
    const val FOLDER_ID_ARG = "FOLDER_ID_ARG"
    const val NOTE_ID_ARG = "NOTE_ID_ARG"
    const val GOAL_ID_ARG = "GOAL_ID_ARG"

}