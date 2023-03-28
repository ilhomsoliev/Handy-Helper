package com.ikcollab.budget.category.bottomSheetAddCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.budget.category.BudgetCategoryState
import com.ikcollab.domain.usecase.budget.category.InsertBudgetCategoryUseCase
import com.ikcollab.model.dto.budget.BudgetCategoryDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetAddCategoryViewModel @Inject constructor(
    private val insertBudgetCategoryUseCase: InsertBudgetCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BottomSheetAddCategoryState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BottomSheetAddCategoryState()
    )
    private val channel = Channel<BottomSheetAddCategoryOneTimeEvent>()
    val flow = channel.receiveAsFlow()

    fun onEvent(event: BottomSheetAddCategoryEvent) {
        when (event) {
            is BottomSheetAddCategoryEvent.OnLoad -> {
                _state.update {
                    it.copy(type = event.type)
                }
            }
            is BottomSheetAddCategoryEvent.OnCategoryNameChange -> {
                if (event.value.endsWith('\n')) {
                    onEvent(BottomSheetAddCategoryEvent.OnAddClick)
                    return
                }
                _state.update {
                    it.copy(
                        categoryName = event.value
                    )
                }
            }
            is BottomSheetAddCategoryEvent.OnAddClick -> {
                if (_state.value.categoryName.isEmpty() && _state.value.type.isEmpty()) return
                viewModelScope.launch {
                    insertBudgetCategoryUseCase(
                        BudgetCategoryDto(
                            name = _state.value.categoryName,
                            dateCreated = System.currentTimeMillis(),
                            type = _state.value.type
                        )
                    )
                    channel.send(BottomSheetAddCategoryOneTimeEvent.CloseBottomSheet)
                }
            }
            else -> {

            }
        }
    }

}