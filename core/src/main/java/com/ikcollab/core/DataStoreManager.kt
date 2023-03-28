package com.ikcollab.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        val NOTIFICATIONS_REPETITIONS_KEY = intPreferencesKey("notifications_repetitions")
        val START_AT_NOTIFICATION_KEY = intPreferencesKey("start_at_notification")
        val END_AT_NOTIFICATION_KEY = intPreferencesKey("end_at_notification")
        val LANGUAGE_SHORTCUT_KEY = stringPreferencesKey("LANGUAGE_SHORTCUT_KEY")
        val IS_NOTIFICATION_ON_KEY = booleanPreferencesKey("IS_NOTIFICATION_ON_KEY")
    }

    suspend fun updateNotificationsRepetition(times: Int) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_REPETITIONS_KEY] = times
        }
    }


    suspend fun updateStartAtNotification(date: Int) {
        context.dataStore.edit { preferences ->
            preferences[START_AT_NOTIFICATION_KEY] = date
        }
    }

    suspend fun updateEndAtNotification(date: Int) {
        context.dataStore.edit { preferences ->
            preferences[END_AT_NOTIFICATION_KEY] = date
        }
    }

    suspend fun updateLanguage(id: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_SHORTCUT_KEY] = id
        }
    }

    suspend fun updateIsNotificationOn(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_NOTIFICATION_ON_KEY] = value
        }
    }

    val getNotificationRepetition = context.dataStore.data.map {
        it[NOTIFICATIONS_REPETITIONS_KEY] ?: 5
    }
    val getStartAtNotification = context.dataStore.data.map {
        it[START_AT_NOTIFICATION_KEY] ?: 10
    }
    val getEndAtNotification = context.dataStore.data.map {
        it[END_AT_NOTIFICATION_KEY] ?: 20
    }
    val getLanguageId = context.dataStore.data.map {
        it[LANGUAGE_SHORTCUT_KEY] ?: "en"
    }
    val getIsNotificationOn = context.dataStore.data.map {
        it[IS_NOTIFICATION_ON_KEY] ?: false
    }
}