package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

import com.ikcollab.model.dto.budget.BudgetCategoryDto

sealed class BottomSheetAddStoryBudgetEvent {
    data class OnLoad(val type: String) : BottomSheetAddStoryBudgetEvent()
    data class OnAmountChange(val value: String) : BottomSheetAddStoryBudgetEvent()
    data class OnCommentChange(val value: String) : BottomSheetAddStoryBudgetEvent()
    data class OnDateChange(val value: Long) : BottomSheetAddStoryBudgetEvent()
    data class OnCategoryPicked(val value: BudgetCategoryDto) : BottomSheetAddStoryBudgetEvent()
    object OnAddClick : BottomSheetAddStoryBudgetEvent()
    data class OnDialogStatusChange(val value: Boolean) : BottomSheetAddStoryBudgetEvent()
}

sealed class BottomSheetAddStoryBudgetOneTimeEvent {
    object CloseBottomSheet : BottomSheetAddStoryBudgetOneTimeEvent()

}