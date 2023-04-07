package com.ikcollab.handyhelper.app.navigation

import com.ikcollab.core.Constants
import com.ikcollab.handyhelper.app.navigation.bottomSheet.BottomSheets

sealed class Screens(val route: String) {
    object BudgetScreen : Screens("BudgetScreen")
    object ChoresScreen : Screens("ChoresScreen")
    object GoalsScreen : Screens("GoalsScreen")
    object LanguagesScreen : Screens("LanguagesScreen")
    object GoalStepsScreen : Screens("GoalsStepsScreen/{${Constants.GOAL_ID_ARG}}")
    object GoalsListScreen : Screens("GoalsListScreen")
    object NotesScreen : Screens("NotesScreen")
    object FoldersNoteScreen : Screens("FoldersNoteScreen/{${Constants.FOLDER_ID_ARG}}")
    object AddNoteScreen :
        Screens("AddNoteScreen/{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}")

    object ShowDetailsOfNoteScreen :
        Screens("ShowDetailsOfNoteScreen/{${Constants.FOLDER_ID_ARG}}/{${Constants.NOTE_ID_ARG}}")

    object SearchNotesScreen : Screens("SearchNotesScreen")
    object PickThemeScreen : Screens("PickThemeScreen")
    object SettingsScreen : Screens("SettingsScreen")
    object TrackerScreen : Screens("TrackerScreen")
    object TodoListScreen : Screens("TodoListScreen")
    object TodoCategoryScreen : Screens("TodoCategoryScreen")
    object BudgetCategoryScreen : Screens("BudgetCategoryScreen")
}

fun doesScreenHaveMenu(currentScreen: String): Boolean =
    currentScreen != Screens.GoalsListScreen.route &&
            currentScreen != Screens.AddNoteScreen.route &&
            currentScreen != Screens.FoldersNoteScreen.route &&
            currentScreen != Screens.GoalStepsScreen.route &&
            currentScreen != Screens.ShowDetailsOfNoteScreen.route &&
            currentScreen != BottomSheets.AddStepGoalSheet.route &&
            currentScreen != BottomSheets.AddBudgetCategorySheet.route &&
            currentScreen != Screens.SearchNotesScreen.route &&
            currentScreen != Screens.BudgetCategoryScreen.route &&
            currentScreen != Screens.LanguagesScreen.route &&
            currentScreen != Screens.SettingsScreen.route &&
            currentScreen != Screens.TodoCategoryScreen.route

fun isDrawerGesturesEnabled(currentScreen: String): Boolean =
    currentScreen != Screens.GoalsListScreen.route &&
            currentScreen != Screens.FoldersNoteScreen.route &&
            currentScreen != Screens.LanguagesScreen.route &&
            currentScreen != Screens.SettingsScreen.route &&
            currentScreen != Screens.AddNoteScreen.route &&
            currentScreen != Screens.GoalStepsScreen.route &&
            currentScreen != Screens.BudgetCategoryScreen.route &&
            currentScreen != Screens.ShowDetailsOfNoteScreen.route &&
            currentScreen != Screens.TodoCategoryScreen.route &&
            currentScreen != Screens.SearchNotesScreen.route

fun doesScreenHaveBottomBar(currentScreen: String) =
    currentScreen != Screens.GoalsListScreen.route &&
            currentScreen != Screens.FoldersNoteScreen.route &&
            currentScreen != Screens.AddNoteScreen.route &&
            currentScreen != Screens.GoalStepsScreen.route &&
            currentScreen != Screens.ShowDetailsOfNoteScreen.route &&
            currentScreen != Screens.SearchNotesScreen.route &&
            currentScreen != Screens.LanguagesScreen.route &&
            currentScreen != Screens.SettingsScreen.route &&
            currentScreen != Screens.BudgetCategoryScreen.route &&
            currentScreen != BottomSheets.AddStepGoalSheet.route &&
            currentScreen != BottomSheets.AddBudgetCategorySheet.route &&
            currentScreen != Screens.TodoCategoryScreen.route

fun getTopBarTitle(currentScreen: String) = when (currentScreen) {
    Screens.GoalsScreen.route, BottomSheets.AddGoalSheet.route, Screens.GoalsListScreen.route -> "Goals"
    Screens.ChoresScreen.route -> "To-Do list"
    Screens.TrackerScreen.route -> "Habit Tracker"
    Screens.NotesScreen.route -> "Notes"
    Screens.BudgetScreen.route, BottomSheets.AddBudgetStorySheet.route -> "Budget"
    Screens.BudgetCategoryScreen.route, BottomSheets.AddBudgetCategorySheet.route -> "Categories"
    Screens.LanguagesScreen.route -> "Languages"
    Screens.SettingsScreen.route -> "Settings"
    Screens.FoldersNoteScreen.route -> Constants.MAIN_FOLDER_NAME.value
    Screens.AddNoteScreen.route -> if (Constants.WHICH_NOTE.value == Constants.EDIT_NOTE) "Edit note" else "Add note"
    else -> ""
}