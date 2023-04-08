package com.ilhomsoliev.settings

data class SettingsState(
    val fontName: String? = null,
    val fontSize: Int? = null,
    val isNotificationOn: Boolean? = null,
    val notificationTime: Long? = null,
    val isExpenseRecodingReminderOn: Boolean? = null,
    val expenseRecodingReminderTime: Long? = null,
)