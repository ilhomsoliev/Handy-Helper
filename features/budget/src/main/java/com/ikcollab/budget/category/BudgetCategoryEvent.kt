package com.ikcollab.budget.category

sealed class BudgetCategoryEvent {
    data class DeleteCategory(val id: Int) : BudgetCategoryEvent()
    data class OnCategoryIdChange(val id: Int) : BudgetCategoryEvent()
    data class OnCategoryNameChange(val name: String) : BudgetCategoryEvent()
    data class OnCategoryDialogStateChange(val state: Boolean) : BudgetCategoryEvent()
    data class OnEditClick(val type:String,val id: Int) : BudgetCategoryEvent()
    data class OpenBottomSheet(val type: String) : BudgetCategoryEvent()
}