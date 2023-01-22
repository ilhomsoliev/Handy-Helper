package com.ikcollab.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toMMDDYYYY():String{
    return DateTimeFormatter.ofPattern("MM/dd/yyyy")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toMMMDD():String{
    return DateTimeFormatter.ofPattern("MMM dd")
        .withZone(ZoneId.systemDefault())
        .format(Instant.ofEpochMilli(this))
}
