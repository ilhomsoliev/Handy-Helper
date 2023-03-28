package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

data class BottomSheetAddStoryBudgetState(
    val comment: String = "",
    val date: Long = System.currentTimeMillis(),
    val amount: Int = 0,
    val type: String = "",
)