package com.ikcollab.handyhelper.app.navigation

sealed class Screens(val route: String) {
    object BudgetScreen : Screens("HomeScreen")
    object ChoresScreen : Screens("ChoresScreen")
    object GoalsScreen : Screens("GoalsScreen")
    object NotesScreen : Screens("NotesScreen")
    object PickThemeScreen : Screens("PickThemeScreen")
    object SettingsScreen : Screens("SettingsScreen")
    object TrackerScreen : Screens("TrackerScreen")
}