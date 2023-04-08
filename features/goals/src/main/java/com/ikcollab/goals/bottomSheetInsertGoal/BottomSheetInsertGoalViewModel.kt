package com.ikcollab.goals.bottomSheetInsertGoal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.core.toMMDDYYYY
import com.ikcollab.domain.usecase.goals.goal.GetGoalByIdUseCase
import com.ikcollab.domain.usecase.goals.goal.InsertGoalUseCase
import com.ikcollab.domain.usecase.todo_list.todo.InsertTodoUseCase
import com.ikcollab.model.dto.goals.GoalDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetInsertGoalViewModel @Inject constructor(
    private val insertGoalUseCase: InsertGoalUseCase,
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(BottomSheetInsertGoalState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BottomSheetInsertGoalState()
    )

    private val channel = Channel<BottomSheetInsertGoalOneTimeEvent>()
    val flow = channel.receiveAsFlow()

    fun onEvent(event: BottomSheetInsertGoalEvent) {
        when (event) {
            is BottomSheetInsertGoalEvent.OnLoad -> {
                if (event.goalId == -1) {
                    _state.update {
                        it.copy(goal = GoalDto())
                    }
                } else {
                    viewModelScope.launch {
                        _state.update {
                            it.copy(goal = getGoalByIdUseCase(event.goalId))
                        }
                    }
                }
            }
            is BottomSheetInsertGoalEvent.InsertGoalToDatabase -> {
                if (_state.value.goal?.name!!.isEmpty()) return
                viewModelScope.launch {
                    _state.value.goal?.let { insertGoalUseCase(it) }
                    channel.send(BottomSheetInsertGoalOneTimeEvent.CloseBottomSheet)
                }
            }
            is BottomSheetInsertGoalEvent.OnStartTimeChange -> {
                _state.update {
                    it.copy(goal = _state.value.goal!!.copy(dateStart = event.value))
                }
            }
            is BottomSheetInsertGoalEvent.OnEndTimeChange -> {
                _state.update {
                    it.copy(goal = _state.value.goal!!.copy(dateEnd = event.value))
                }
            }
            is BottomSheetInsertGoalEvent.OnNewGoalNameChange -> {
                if (event.value.endsWith('\n')) {
                    onEvent(BottomSheetInsertGoalEvent.InsertGoalToDatabase)
                    return
                }
                _state.update {
                    it.copy(goal = _state.value.goal!!.copy(name = event.value))
                }
            }
            else -> {

            }
        }
    }
}