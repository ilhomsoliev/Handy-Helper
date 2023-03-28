package com.ikcollab.budget.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.budget.category.GetBudgetCategoriesByTypeUseCase
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import com.ikcollab.model.local.budget.INCOME_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val getBudgetCategoriesByTypeUseCase: GetBudgetCategoriesByTypeUseCase,

    ) : ViewModel() {
    private val _state = MutableStateFlow(BudgetState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BudgetState()
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

    fun onEvent(event: BudgetEvent) {
        when (event) {
            else -> {

            }
        }
    }
}