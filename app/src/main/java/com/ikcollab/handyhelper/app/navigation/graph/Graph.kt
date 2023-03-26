package com.ikcollab.handyhelper.app.navigation.graph

sealed class Graph(val route: String) {
    object GoalsGraph : Graph("GoalsGraph")
    object BudgetGraph : Graph("BudgetGraph")
    object ChoresGraph : Graph("ChoresGraph")
    object NotesGraph : Graph("NotesGraph")
    object TodoListGraph : Graph("TodoListGraph")
}