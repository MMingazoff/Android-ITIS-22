package com.itis.androidtestproject.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.prettify(): String =
    SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
        .format(this.time)
