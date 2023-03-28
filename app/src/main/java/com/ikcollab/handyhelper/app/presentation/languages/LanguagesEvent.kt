package com.ikcollab.handyhelper.app.presentation.languages

sealed class LanguagesEvent {
    object OnBackClick : LanguagesEvent()
    data class OnLanguageItemClick(val shortcut: String) : LanguagesEvent()
}