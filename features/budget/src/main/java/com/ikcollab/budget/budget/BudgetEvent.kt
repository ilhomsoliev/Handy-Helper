package com.ikcollab.budget.budget

import com.ikcollab.budget.category.BudgetCategoryEvent

sealed class BudgetEvent {

    data class OpenBottomSheet(val type: String, val categoryId: Int = -1) : BudgetEvent()
    data class DeleteStory(val storyId: Int) : BudgetEvent()
    data class OnEditStory( val storyId: Int) : BudgetEvent()

}