package com.ikcollab.notes.presentation.bottomSheetInsertFolder

sealed class BottomSheetInsertFolderEvent {
    data class OnFolderNameChange(val value: String) : BottomSheetInsertFolderEvent()
    data class OnFolderIdChange(val value: Int) : BottomSheetInsertFolderEvent()
    object InsertFolder : BottomSheetInsertFolderEvent()
}
sealed class BottomSheetInsertFolderOneTimeEvent {
    object CloseBottomSheet : BottomSheetInsertFolderOneTimeEvent()
}