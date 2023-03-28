package com.ikcollab.handyhelper.app.presentation.languages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ikcollab.handyhelper.R
import com.ikcollab.handyhelper.app.presentation.languages.components.CustomTopAppBar
import com.ikcollab.handyhelper.app.presentation.languages.components.LanguageItem

@Composable
fun LanguagesScreen(
    state: LanguagesState,
    onEvent: (LanguagesEvent) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (state.selectedLanguage != null) {
            LazyColumn() {
                items(state.languages) {
                    LanguageItem(name = it.name, isActive = it.id == state.selectedLanguage.id) {
                        onEvent(LanguagesEvent.OnLanguageItemClick(it.shortCut))
                    }
                }
            }
        }
    }
}