package com.ikcollab.handyhelper.app.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.goal.InsertGoalUseCase
import com.ikcollab.model.dto.goals.GoalDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val insertGoalUseCase: InsertGoalUseCase
) : ViewModel() {
    private val _newGoalName = mutableStateOf("")
    val newGoalName: State<String> = _newGoalName

    private val _newGoalStartDate = mutableStateOf(System.currentTimeMillis())
    val newGoalStartDate: State<Long> = _newGoalStartDate

    private val _newGoalEndDate = mutableStateOf(System.currentTimeMillis())
    val newGoalEndDate: State<Long> = _newGoalEndDate

    fun changeNewGoalName(value: String) {
        _newGoalName.value = value
    }

    fun changeNewGoalStartDate(value: Long) {
        _newGoalStartDate.value = value
    }

    fun changeNewGoalEndDate(value: Long) {
        _newGoalEndDate.value = value
    }

    fun addGoalToDatabase(onDone:()->Unit) {
        if (_newGoalName.value.isEmpty()) return

        viewModelScope.launch {
            insertGoalUseCase(
                GoalDto(
                    id = null,
                    name = _newGoalName.value,
                    stepsCount = 0,
                    completedStepsCount = 0,
                    dateCreated = System.currentTimeMillis(),
                    dateStart = _newGoalStartDate.value,
                    dateEnd = _newGoalEndDate.value,
                )
            )
            _newGoalEndDate.value = System.currentTimeMillis()
            _newGoalStartDate.value = System.currentTimeMillis()
            _newGoalName.value = ""
            onDone()
        }
    }

}