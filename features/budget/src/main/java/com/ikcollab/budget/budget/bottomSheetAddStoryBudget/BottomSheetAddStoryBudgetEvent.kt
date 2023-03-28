package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

sealed class BottomSheetAddStoryBudgetEvent {
    data class OnLoad(val type: String) : BottomSheetAddStoryBudgetEvent()
    data class OnAmountChange(val value: String) : BottomSheetAddStoryBudgetEvent()
    data class OnCommentChange(val value: String) : BottomSheetAddStoryBudgetEvent()
    data class OnDateChange(val value: Long) : BottomSheetAddStoryBudgetEvent()
    object OnAddClick : BottomSheetAddStoryBudgetEvent()
}

sealed class BottomSheetAddStoryBudgetOneTimeEvent {
    object CloseBottomSheet : BottomSheetAddStoryBudgetOneTimeEvent()

}