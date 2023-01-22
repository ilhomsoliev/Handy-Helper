package com.ikcollab.goals.goalsListScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.goal.GetGoalsWithStepsUseCase
import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.goals.GoalWithStepsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsListScreenViewModel @Inject constructor(
    private val getGoalsWithStepsUseCase: GetGoalsWithStepsUseCase,
) : ViewModel() {

    private val _goalsWithSteps = mutableStateListOf<GoalWithStepsDto>()
    val goalsWithSteps: List<GoalWithStepsDto> = _goalsWithSteps

    init {
        getGoalsWithSteps()
    }

    private fun getGoalsWithSteps() {
        viewModelScope.launch(Dispatchers.IO) {
            getGoalsWithStepsUseCase().onEach {
                _goalsWithSteps.clear()
                _goalsWithSteps.addAll(it)
            }.launchIn(viewModelScope)
        }
    }

}