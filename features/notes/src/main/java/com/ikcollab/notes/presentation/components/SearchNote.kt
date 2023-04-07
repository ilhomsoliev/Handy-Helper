package com.ikcollab.notes.presentation.components

import java.util.*


fun searchNote(title:String,description:String,search:String):Boolean =
    title.toUpperCase(Locale.ROOT).contains(search.trim().toUpperCase(Locale.ROOT)) ||
            description.toUpperCase(Locale.ROOT).contains(search.trim().toUpperCase(Locale.ROOT))