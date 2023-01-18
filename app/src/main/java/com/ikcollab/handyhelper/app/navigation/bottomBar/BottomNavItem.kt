package com.ikcollab.handyhelper.app.navigation.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.ikcollab.handyhelper.app.navigation.Screens

sealed class BottomNavItem(var title:String, var icon:ImageVector, var screen_route:String){

    object Goals : BottomNavItem("Goals", Icons.Default.Flag, Screens.GoalsScreen.route)
    object ToDoList: BottomNavItem("To-Do list",Icons.Default.ListAlt, Screens.ChoresScreen.route)
    object Tracker: BottomNavItem("Tracker",Icons.Default.Cached, Screens.TrackerScreen.route)
    object Notes: BottomNavItem("Notes",Icons.Default.Note, Screens.NotesScreen.route)
    object Budget: BottomNavItem("Budget",Icons.Default.AttachMoney, Screens.BudgetScreen.route)
}