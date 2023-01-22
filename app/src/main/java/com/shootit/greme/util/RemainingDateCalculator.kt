package com.shootit.greme.util

import java.text.SimpleDateFormat
import java.util.*

object RemainingDateCalculator {

    fun String.serverTimeToDDay(): Int {
        val dateInString = this
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'")
        val deadLine = dateFormat.parse(dateInString)
        val diff = (deadLine.time - getCurrentDate().time) / (60 * 60 * 24 * 1000) + 1
        return diff.toInt()
    }

    private fun getCurrentDate() : Date {
        return Calendar.getInstance().time
    }
}