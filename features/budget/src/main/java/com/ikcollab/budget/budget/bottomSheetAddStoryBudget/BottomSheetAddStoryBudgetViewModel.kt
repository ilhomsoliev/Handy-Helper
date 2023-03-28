package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryOneTimeEvent
import com.ikcollab.budget.category.bottomSheetAddCategory.BottomSheetAddCategoryState
import com.ikcollab.domain.usecase.budget.story.InsertBudgetStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BottomSheetAddStoryBudgetViewModel @Inject constructor(
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
    fun onEvent(event: BottomSheetAddStoryBudgetEvent) {
        when (event) {
            is BottomSheetAddStoryBudgetEvent.OnLoad->{

            }
            else -> {

            }
        }
    }
}