package com.ikcollab.goals.bottomSheetInsertStepGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.stepGoal.InsertStepGoalUseCase
import com.ikcollab.goals.bottomSheetInsertGoal.BottomSheetInsertGoalEvent
import com.ikcollab.goals.bottomSheetInsertGoal.BottomSheetInsertGoalOneTimeEvent
import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.goals.StepGoalDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class BottomSheetInsertStepGoalViewModel @Inject constructor(
    private val insertStepGoalUseCase: InsertStepGoalUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(BottomSheetInsertStepGoalState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BottomSheetInsertStepGoalState()
    )

    private val channel = Channel<BottomSheetInsertStepGoalOneTimeEvent>()
    val flow = channel.receiveAsFlow()

    fun onEvent(event: BottomSheetInsertStepGoalEvent) {
        when (event) {
            is BottomSheetInsertStepGoalEvent.OnInit -> {
                _state.update {
                    it.copy(goalId = event.goalId)
                }
            }
            is BottomSheetInsertStepGoalEvent.OnStepGoalValueChange -> {
                if (event.value.endsWith('\n')) {
                    onEvent(BottomSheetInsertStepGoalEvent.OnAddClick)
                    return
                }
                _state.update {
                    it.copy(stepGoalValue = event.value)
                }
            }
            is BottomSheetInsertStepGoalEvent.OnDeadlineChange -> {
                _state.update {
                    it.copy(deadline = event.value)
                }
            }
            is BottomSheetInsertStepGoalEvent.OnAddClick -> {
                if (_state.value.stepGoalValue.isEmpty() || _state.value.goalId == 0) return
                viewModelScope.launch {
                    insertStepGoalUseCase(
                        StepGoalDto(
                            id = null,
                            name = _state.value.stepGoalValue,
                            isCompleted = false,
                            dateCreated = _state.value.deadline,
                            deadline = _state.value.deadline,
                            goalId = _state.value.goalId,
                        )
                    )
                    channel.send(BottomSheetInsertStepGoalOneTimeEvent.CloseBottomSheet)
                }
            }
            else -> {

            }
        }
    }


}