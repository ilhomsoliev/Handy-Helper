package com.ikcollab.handyhelper.app.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(

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

    fun addGoalToDatabase(){

    }

}