package com.ikcollab.goals.goalSteps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.goal.GetGoalByIdUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.DeleteStepGoalByIdUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.GetStepGoalByIdUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.GetStepsGoalByGoalIdUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.InsertStepGoalUseCase
import com.ikcollab.model.dto.toStepGoalDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalStepsViewModel @Inject constructor(
    private val getStepsGoalByGoalIdUseCase: GetStepsGoalByGoalIdUseCase,
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
    private val insertStepGoalUseCase: InsertStepGoalUseCase,
    private val deleteStepGoalByIdUseCase: DeleteStepGoalByIdUseCase,
    private val getStepGoalByIdUseCase: GetStepGoalByIdUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(GoalStepsState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        GoalStepsState()
    )
    fun onEvent(event: GoalStepsEvent) {
        when (event) {
            is GoalStepsEvent.OnInit -> {
                getStepsGoalByGoalIdUseCase(goalId = event.goalId).onEach { list ->
                    _state.update {
                        it.copy(
                            steps = list
                        )
                    }
                }.launchIn(viewModelScope)
                updateGoal(event.goalId)
            }
            is GoalStepsEvent.OnMarkAsCompletedClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val stepGoal = getStepGoalByIdUseCase(event.id)!!.toStepGoalDto()
                    insertStepGoalUseCase(
                        stepGoal.copy(
                            isCompleted = true
                        )
                    )
                    updateGoal(stepGoal.goalId)
                }
            }
            is GoalStepsEvent.OnMarkNotAsCompletedClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val stepGoal = getStepGoalByIdUseCase(event.id)!!.toStepGoalDto()
                    insertStepGoalUseCase(
                        stepGoal.copy(
                            isCompleted = false
                        )
                    )
                    updateGoal(stepGoal.goalId)
                }
            }
            is GoalStepsEvent.OnDeleteStepGoalClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val stepGoal = getStepGoalByIdUseCase(event.id)!!.toStepGoalDto()
                    deleteStepGoalByIdUseCase(stepGoal)
                    updateGoal(stepGoal.goalId)
                }
            }
            else -> {

            }
        }
    }

    private fun updateGoal(goalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    goal = getGoalByIdUseCase(goalId)
                )
            }
        }
    }

}