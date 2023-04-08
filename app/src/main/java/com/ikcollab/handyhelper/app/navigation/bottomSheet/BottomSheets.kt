package com.ikcollab.handyhelper.app.navigation.bottomSheet

import com.ikcollab.core.Constants

sealed class BottomSheets(val route: String) {
    object AddGoalSheet : BottomSheets("AddGoalSheet/{${Constants.GOAL_ID_ARG}}")
    object AddFolderSheet : BottomSheets("AddFolderSheet/{${Constants.FOLDER_ID_ARG}}/{${Constants.FOLDER_NAME_ARG}}")
    object AddBudgetCategorySheet :
        BottomSheets("AddBudgetCategorySheet/{${Constants.CATEGORY_TYPE_ID_ARG}}/{${Constants.CATEGORY_ID_ARG}}")

    object AddBudgetStorySheet :
        BottomSheets("AddBudgetStorySheet/{${Constants.CATEGORY_TYPE_ID_ARG}}/{${Constants.STORY_ID_ARG}}/{${Constants.CATEGORY_ID_ARG}}")

    object AddStepGoalSheet : BottomSheets("AddStepGoalSheet/{${Constants.GOAL_ID_ARG}}")
}