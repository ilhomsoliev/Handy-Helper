package com.ikcollab.budget.budget

import com.ikcollab.budget.category.BudgetCategoryEvent

sealed class BudgetEvent {

    data class OpenBottomSheet(val type:String) : BudgetEvent()

}