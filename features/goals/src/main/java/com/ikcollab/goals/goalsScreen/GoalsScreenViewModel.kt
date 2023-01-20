package com.ikcollab.goals.goalsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.goal.GetGoalsUseCase
import com.ikcollab.model.dto.goals.GoalDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GoalsScreenViewModel @Inject constructor(
    private val getGoalsUseCase: GetGoalsUseCase
) : ViewModel() {
    private val _goals = mutableStateListOf<GoalDto>()
    val goals: List<GoalDto> = _goals

    init {
        getGoals()
    }

    private fun getGoals() {
        getGoalsUseCase().onEach {
            _goals.clear()
            _goals.addAll(it)
        }.launchIn(viewModelScope)
    }
}