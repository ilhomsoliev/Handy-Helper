package com.ikcollab.goals.goalsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.goal.DeleteGoalByIdUseCase
import com.ikcollab.domain.usecase.goals.goal.GetGoalsUseCase
import com.ikcollab.model.dto.goals.GoalDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class GoalsViewModel @Inject constructor(
    private val getGoalsUseCase: GetGoalsUseCase,
    private val deleteGoalByIdUseCase: DeleteGoalByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(GoalsState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        GoalsState()
    )

    init {
        getGoals()
    }

    private fun getGoals() {
        getGoalsUseCase().onEach { list ->
            _state.update {
                it.copy(
                    goals = list
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: GoalsEvent) {
        when (event) {
            is GoalsEvent.OnDeleteStepGoalClick -> {
                viewModelScope.launch {
                    deleteGoalByIdUseCase(event.id)

                }
            }
            else -> {

            }
        }
    }
}