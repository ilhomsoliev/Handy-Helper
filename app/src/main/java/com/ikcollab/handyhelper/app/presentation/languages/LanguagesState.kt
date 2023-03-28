package com.ikcollab.handyhelper.app.presentation.languages

import com.ikcollab.core.model.Language

data class LanguagesState(
    val languages: List<Language> = getLanguages(),
    val selectedLanguage: Language? = null,
)

fun getLanguages() =
    listOf<Language>(
        Language(1, "English", "en"),
        Language(2, "Russian", "ru"),
        Language(3, "Tajik", "tj")
    )
