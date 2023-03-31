package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryOneTimeEvent
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryState
import com.ikcollab.domain.usecase.budget.category.GetBudgetCategoriesByTypeUseCase
import com.ikcollab.domain.usecase.budget.category.GetBudgetCategoryByIdUseCase
import com.ikcollab.domain.usecase.budget.story.GetBudgetStoryByIdUseCase
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
    private val insertBudgetStoryUseCase: InsertBudgetStoryUseCase,
    private val getBudgetStoryByIdUseCase: GetBudgetStoryByIdUseCase,
    private val getBudgetCategoryById: GetBudgetCategoryByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(BottomSheetAddStoryBudgetState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BottomSheetAddStoryBudgetState()
    )
    private val channel = Channel<BottomSheetAddStoryBudgetOneTimeEvent>()
    val flow = channel.receiveAsFlow()

    fun onEvent(event: BottomSheetAddStoryBudgetEvent) {
        when (event) {
            is BottomSheetAddStoryBudgetEvent.OnLoad -> {
                viewModelScope.launch {
                    if (event.storyId != null) {
                        val story = getBudgetStoryByIdUseCase(event.storyId)
                        _state.update {
                            it.copy(story = story)
                        }
                        return@launch
                    }
                    if (event.categoryId != null) {
                        val category = getBudgetCategoryById(event.categoryId)
                        Log.d("Hello", category.toString())
                        _state.update {
                            it.copy(
                                story = _state.value.story.copy(
                                    categoryId = event.categoryId,
                                    categoryName = category?.name ?: ""
                                )
                            )
                        }
                        return@launch
                    }
                    _state.update {
                        it.copy(story = _state.value.story.copy(type = event.type))
                    }
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
                    it.copy(
                        story = _state.value.story.copy(
                            categoryId = event.value.id!!,
                            categoryName = event.value.name
                        )
                    )
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnDateChange -> {
                _state.update {
                    it.copy(story = _state.value.story.copy(dateCreated = event.value))
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnCommentChange -> {
                _state.update {
                    it.copy(story = _state.value.story.copy(comment = event.value))
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnAmountChange -> {
                _state.update {
                    it.copy(story = _state.value.story.copy(value = if (event.value.isEmpty()) 0 else event.value.toInt()))
                }
            }
            is BottomSheetAddStoryBudgetEvent.OnAddClick -> {
                if (_state.value.story.value == 0 || _state.value.story.categoryId == -1) return
                viewModelScope.launch {
                    insertBudgetStoryUseCase(_state.value.story)
                    channel.send(BottomSheetAddStoryBudgetOneTimeEvent.CloseBottomSheet)
                }
            }
            else -> {

            }
        }
    }
}