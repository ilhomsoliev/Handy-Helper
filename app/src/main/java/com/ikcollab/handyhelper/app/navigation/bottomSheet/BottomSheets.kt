package com.ikcollab.handyhelper.app.navigation.bottomSheet

import com.ikcollab.core.Constants

sealed class  BottomSheets (val route: String) {
    object AddGoalSheet:BottomSheets("AddGoalSheet")
    object AddStepGoalSheet:BottomSheets("AddStepGoalSheet/{${Constants.GOAL_ID_ARG}}")
}