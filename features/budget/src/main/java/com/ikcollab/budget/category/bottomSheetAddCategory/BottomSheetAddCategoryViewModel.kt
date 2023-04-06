package com.ikcollab.budget.category.bottomSheetAddCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.budget.category.BudgetCategoryState
import com.ikcollab.core.showLog
import com.ikcollab.domain.usecase.budget.category.GetBudgetCategoryByIdUseCase
import com.ikcollab.domain.usecase.budget.category.InsertBudgetCategoryUseCase
import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetAddCategoryViewModel @Inject constructor(
    private val insertBudgetCategoryUseCase: InsertBudgetCategoryUseCase,
    private val getBudgetCategoryByIdUseCase: GetBudgetCategoryByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        BottomSheetAddCategoryState()
    )

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
                if (event.id != null) {
                    viewModelScope.launch {
                        _state.update {
                            it.copy(
                                category = getBudgetCategoryByIdUseCase(event.id)!!,
                            )
                        }
                        _state.value.category.showLog()
                    }
                    return
                }
                _state.update {
                    it.copy(
                        category = BudgetCategoryDto(
                            null, "", System.currentTimeMillis(),
                            event.type
                        ),
                    )
                }
            }
            is BottomSheetAddCategoryEvent.OnCategoryNameChange -> {
                if (event.value.endsWith('\n')) {
                    onEvent(BottomSheetAddCategoryEvent.OnAddClick)
                    return
                }
                _state.update {
                    it.copy(
                        category = _state.value.category.copy(name = event.value)
                    )
                }
            }
            is BottomSheetAddCategoryEvent.OnAddClick -> {
                if (_state.value.category.name.isEmpty() && _state.value.category.type.isEmpty()) return
                viewModelScope.launch {
                    _state.value.category.showLog()
                    insertBudgetCategoryUseCase(_state.value.category)
                    channel.send(BottomSheetAddCategoryOneTimeEvent.CloseBottomSheet)
                }
            }
            else -> {

            }
        }
    }

}