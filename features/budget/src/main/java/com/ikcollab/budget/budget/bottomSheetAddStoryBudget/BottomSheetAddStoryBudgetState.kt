package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

import com.ikcollab.model.dto.budget.BudgetCategoryDto

data class BottomSheetAddStoryBudgetState(
    val comment: String = "",
    val date: Long = System.currentTimeMillis(),
    val categories: List<BudgetCategoryDto> = emptyList(),
    val pickedCategory: BudgetCategoryDto? = null,
    val amount: Int = 0,
    val type: String = "",
    val isDialogActive: Boolean = false,
)