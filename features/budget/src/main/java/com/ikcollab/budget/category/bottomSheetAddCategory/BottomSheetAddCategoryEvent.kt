package com.ikcollab.budget.category.bottomSheetAddCategory

sealed class BottomSheetAddCategoryEvent {
    data class OnLoad(val type: String, val id: Int?) : BottomSheetAddCategoryEvent()
    data class OnCategoryNameChange(val value: String) : BottomSheetAddCategoryEvent()
    object OnAddClick : BottomSheetAddCategoryEvent()
}

sealed class BottomSheetAddCategoryOneTimeEvent {
    object CloseBottomSheet : BottomSheetAddCategoryOneTimeEvent()
}