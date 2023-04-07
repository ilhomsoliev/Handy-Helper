package com.ikcollab.budget.budget

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.budget.category.GetBudgetCategoriesByTypeUseCase
import com.ikcollab.domain.usecase.budget.category.GetBudgetCategoriesWithSumByTypeUseCase
import com.ikcollab.domain.usecase.budget.story.*
import com.ikcollab.model.dto.budget.BudgetStoryDto
import com.ikcollab.model.local.budget.BudgetStoryEntity
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import com.ikcollab.model.local.budget.INCOME_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val getBudgetCategoriesByTypeUseCase: GetBudgetCategoriesByTypeUseCase,
    private val getBudgetStoriesByTypeUseCase: GetBudgetStoriesByTypeUseCase,
    private val deleteBudgetStoryByIdUseCase: DeleteBudgetStoryByIdUseCase,
    private val getStorySumByType: GetStorySumByType,
    val getStorySumByCategoryId: GetStorySumByCategoryId
) : ViewModel() {

    private val _state = MutableStateFlow(BudgetState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BudgetState()
    )

    init {
        getStorySumByType(EXPENSES_TYPE).onEach { sum ->
            _state.update {
                it.copy(totalSumExpense = sum ?: 0.0)
            }
        }.launchIn(viewModelScope)

        getStorySumByType(INCOME_TYPE).onEach { sum ->
            _state.update {
                it.copy(totalSumIncome = sum ?: 0.0)
            }
        }.launchIn(viewModelScope)

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

        getBudgetStoriesByTypeUseCase(EXPENSES_TYPE).onEach { list ->
            _state.update {
                it.copy(expensesStories = list)
            }
        }.launchIn(viewModelScope)

        getBudgetStoriesByTypeUseCase(INCOME_TYPE).onEach { list ->
            _state.update {
                it.copy(incomeStories = list)
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: BudgetEvent) {
        when (event) {
            is BudgetEvent.DeleteStory -> {
                viewModelScope.launch {
                    deleteBudgetStoryByIdUseCase(BudgetStoryDto(event.storyId))
                }
            }
            else -> {

            }
        }
    }
}