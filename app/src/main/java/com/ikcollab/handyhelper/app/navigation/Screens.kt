package com.ikcollab.handyhelper.app.navigation

import com.ikcollab.core.Constants

sealed class Screens(val route: String) {
    object BudgetScreen : Screens("HomeScreen")
    object ChoresScreen : Screens("ChoresScreen")
    object GoalsScreen : Screens("GoalsScreen")
    object GoalsListScreen : Screens("GoalsListScreen")
    object NotesScreen : Screens("NotesScreen")
    object FoldersNoteScreen : Screens("FoldersNoteScreen/{${Constants.FOLDER_ID_ARG}}")
    object AddNoteScreen : Screens("AddNoteScreen/{${Constants.FOLDER_ID_ARG}}")
    object PickThemeScreen : Screens("PickThemeScreen")
    object SettingsScreen : Screens("SettingsScreen")
    object TrackerScreen : Screens("TrackerScreen")
}