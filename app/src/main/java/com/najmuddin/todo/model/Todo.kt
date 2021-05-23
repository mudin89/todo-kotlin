package com.najmuddin.todo.model

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Todo {
    var title: String = ""

    var startDate: Date? = null

    var endDate: Date? = null

    var isComplete: Boolean = false

    fun getStartDateAsString() : String{
        var startDate = startDate ?: Calendar.getInstance().getTime()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return dateFormatter.format(startDate)
    }

    fun getEndDateAsString() : String{
        var startDate = endDate ?: Calendar.getInstance().getTime()
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return dateFormatter.format(startDate)
    }

    fun getCountDown() : String{
        val cal = endDate?.time ?: Calendar.getInstance().timeInMillis

        var duration = cal - System.currentTimeMillis()



        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(duration),
                TimeUnit.MILLISECONDS.toMinutes(duration) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

}