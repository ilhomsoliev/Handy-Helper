package com.ikcollab.budget.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.budget.category.DeleteBudgetCategoryByIdUseCase
import com.ikcollab.domain.usecase.budget.category.GetBudgetCategoriesByTypeUseCase
import com.ikcollab.domain.usecase.budget.category.InsertBudgetCategoryUseCase
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import com.ikcollab.model.local.budget.INCOME_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetCategoryViewModel @Inject constructor(
    private val getBudgetCategoriesByTypeUseCase: GetBudgetCategoriesByTypeUseCase,
    private val deleteBudgetCategoryByIdUseCase: DeleteBudgetCategoryByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(BudgetCategoryState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BudgetCategoryState()
    )

    init {
        getBudgetCategoriesByTypeUseCase(EXPENSES_TYPE).onEach { list ->
            _state.update {
                it.copy(expensesCategories = list)
            }
        }.launchIn(viewModelScope)

        getBudgetCategoriesByTypeUseCase(INCOME_TYPE).onEach { list ->
            _state.update {
                it.copy(incomeCategories = list)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: BudgetCategoryEvent) {
        when (event) {
            is BudgetCategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    deleteBudgetCategoryByIdUseCase(event.id)
                }
            }
            else -> {

            }
        }
    }
}