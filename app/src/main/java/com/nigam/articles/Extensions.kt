package com.nigam.articles

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.ln
import kotlin.math.pow

fun String.covertTimeToText(): String {
    var finalTime = ""
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val pasTime: Date? = dateFormat.parse(this)
        val nowTime = Date()
        val dateDiff: Long = nowTime.time - (pasTime?.time ?: 0)
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
        if (second < 60) {
            finalTime = "$second Seconds"
        } else if (minute < 60) {
            finalTime = "$minute Minutes"
        } else if (hour < 24) {
            finalTime = "$hour Hours"
        } else if (day >= 7) {
            finalTime = if (day > 360) {
                (day / 360).toString() + " Years"
            } else if (day > 30) {
                (day / 30).toString() + " Months"
            } else {
                (day / 7).toString() + " Week"
            }
        } else if (day < 7) {
            finalTime = "$day Days"
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return finalTime
}

fun Long.countWithSuffix(): String {
    val suffixChars = "KMGTPE"
    val formatter = DecimalFormat("###.#")
    formatter.roundingMode = RoundingMode.DOWN

    return if (this < 1000.0) {
        formatter.format(this)
    } else {
        val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
        formatter.format(this / 1000.0.pow(exp.toDouble())) + suffixChars[exp - 1]
    }
}