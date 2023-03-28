package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

sealed class BottomSheetAddStoryBudgetEvent {
    data class OnLoad(val type:String) : BottomSheetAddStoryBudgetEvent()
    data class OnCategoryNameChange(val value:String) : BottomSheetAddStoryBudgetEvent()
    object OnAddClick : BottomSheetAddStoryBudgetEvent()
}

sealed class BottomSheetAddStoryBudgetOneTimeEvent{
    object CloseBottomSheet : BottomSheetAddStoryBudgetOneTimeEvent()

}