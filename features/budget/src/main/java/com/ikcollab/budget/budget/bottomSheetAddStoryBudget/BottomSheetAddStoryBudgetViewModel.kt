package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryOneTimeEvent
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryState
import com.ikcollab.domain.usecase.budget.category.GetBudgetCategoriesByTypeUseCase
import com.ikcollab.domain.usecase.budget.story.InsertBudgetStoryUseCase
import com.ikcollab.model.dto.budget.BudgetStoryDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetAddStoryBudgetViewModel @Inject constructor(
    private val getBudgetCategoriesByTypeUseCase: GetBudgetCategoriesByTypeUseCase,
    private val insertBudgetStoryUseCase: InsertBudgetStoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BottomSheetAddStoryBudgetState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BottomSheetAddStoryBudgetState()
    )
    private val channel = Channel<BottomSheetAddStoryBudgetOneTimeEvent>()
    val flow = channel.receiveAsFlow()

    init {
    }

    fun onEvent(event: BottomSheetAddStoryBudgetEvent) {
        when (event) {
            is BottomSheetAddStoryBudgetEvent.OnLoad -> {
                _state.update {
                    it.copy(type = event.type)
                }
                getBudgetCategoriesByTypeUseCase(event.type).onEach { list ->
                    _state.update {
                        it.copy(categories = list)
                    }
                }.launchIn(viewModelScope)
            }
            is BottomSheetAddStoryBudgetEvent.OnDialogStatusChange -> {
                _state.update {
                    it.copy(isDialogActive = event.value)
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnCategoryPicked -> {
                _state.update {
                    it.copy(pickedCategory = event.value)
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnDateChange -> {
                _state.update {
                    it.copy(date = event.value)
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnCommentChange -> {
                _state.update {
                    it.copy(comment = event.value)
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnAmountChange -> {
                _state.update {
                    it.copy(amount = if (event.value.isEmpty()) 0 else event.value.toInt())
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnAddClick -> {
                if (_state.value.amount == 0 || _state.value.pickedCategory?.id == null) return
                viewModelScope.launch {
                    val budgetStory = BudgetStoryDto(
                        comment = _state.value.comment,
                        value = _state.value.amount,
                        type = _state.value.type,
                        categoryId = _state.value.pickedCategory!!.id!!,
                        categoryName = "",
                        dateCreated = System.currentTimeMillis()
                    )
                    insertBudgetStoryUseCase(budgetStory)
                    channel.send(BottomSheetAddStoryBudgetOneTimeEvent.CloseBottomSheet)
                }

            }
            else -> {

            }
        }
    }
}