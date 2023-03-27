package com.ikcollab.goals.bottomSheetInsertGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            is BottomSheetInsertGoalEvent.InsertGoalToDatabase -> {
                if (_state.value.goalName.isEmpty()) return
                viewModelScope.launch {
                    insertGoalUseCase(
                        GoalDto(
                            id = null,
                            name = _state.value.goalName,
                            stepsCount = 0,
                            completedStepsCount = 0,
                            dateCreated = System.currentTimeMillis(),
                            dateStart = _state.value.goalStartDate,
                            dateEnd = _state.value.goalEndDate,
                        )
                    )
                    channel.send(BottomSheetInsertGoalOneTimeEvent.CloseBottomSheet)
                }
            }
            is BottomSheetInsertGoalEvent.OnNewGoalNameChange -> {
                _state.update {
                    it.copy(goalName = event.value)
                }
            }
            else -> {

            }
        }
    }
}