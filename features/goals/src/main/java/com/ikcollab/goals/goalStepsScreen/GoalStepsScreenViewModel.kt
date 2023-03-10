package com.ikcollab.goals.goalStepsScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.goal.GetGoalByIdUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.DeleteStepGoalByIdUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.GetStepsGoalByGoalIdUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.InsertStepGoalUseCase
import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.goals.StepGoalDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalStepsScreenViewModel @Inject constructor(
    private val getStepsGoalByGoalIdUseCase: GetStepsGoalByGoalIdUseCase,
    private val getGoalByIdUseCase: GetGoalByIdUseCase,
    private val insertStepGoalUseCase: InsertStepGoalUseCase,
    private val deleteStepGoalByIdUseCase: DeleteStepGoalByIdUseCase
) :ViewModel() {
    private val _stepsGoal = mutableStateListOf<StepGoalDto>()
    val stepsGoal: List<StepGoalDto> = _stepsGoal

    private val _goal = mutableStateOf<GoalDto?>(null)
    val goal: State<GoalDto?> = _goal

    fun getStepsGoal(goalId:Int){
        getStepsGoalByGoalIdUseCase(goalId = goalId).onEach {
            _stepsGoal.clear()
            _stepsGoal.addAll(it)
        }.launchIn(viewModelScope)
    }

    fun getGoalById(goalId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _goal.value = getGoalByIdUseCase(goalId)
        }
    }

    fun markAsCompleteStepGoal(stepGoalDto: StepGoalDto, onDone:()->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            insertStepGoalUseCase(
                stepGoalDto.copy(
                    isCompleted = true
                )
            )
            onDone()
        }
    }
    fun markAsNotCompleteStepGoal(stepGoalDto: StepGoalDto, onDone:()->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            insertStepGoalUseCase(
                stepGoalDto.copy(
                    isCompleted = false
                )
            )
            onDone()
        }
    }

    fun deleteStepGoal(it: StepGoalDto, onDone: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteStepGoalByIdUseCase(it)
            onDone()
        }
    }

}