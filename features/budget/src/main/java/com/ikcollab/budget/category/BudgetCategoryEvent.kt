package com.ikcollab.budget.category

sealed class BudgetCategoryEvent {
    data class DeleteCategory(val id: Int) : BudgetCategoryEvent()
    data class OpenBottomSheet(val type:String) : BudgetCategoryEvent()
}