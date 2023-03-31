package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.budget.BudgetStoryDto

data class BottomSheetAddStoryBudgetState(
    val story:BudgetStoryDto = BudgetStoryDto(null,"",0,"",System.currentTimeMillis(),-1,""),
    val categories: List<BudgetCategoryDto> = emptyList(),
    val isDialogActive: Boolean = false,
)