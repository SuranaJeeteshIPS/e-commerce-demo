package com.android.basicstructure.core.util

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * File holding all the methods of Date interest.
 * Created by JeeteshSurana.
 */

const val mCommonDayFormat = "dd/MM/YYYY" //ex:- 28/06/1992
const val mCommonMonthFormat = "MM/dd/YYYY" //ex:- 06/28/1992
const val mCommonMonthWithWeekFormat = "EEEE MM/dd/YYYY" //ex:- Monday 06/28/1992
const val mNewsPaperFormat = "EEEE MMMM dd, YYYY" //ex:- Monday  JUNE 28 , 1992
const val mCommonTimeFormat = "hh:mm a"


@SuppressLint("SimpleDateFormat")
fun getConvertDate(GetDate: String): String? {
    var formattedDate: String? = null
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("dd MMM yyyy")
        val date = inputFormat.parse(GetDate)
        formattedDate = outputFormat.format(date!!)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return formattedDate
}

//get you date with Format by Date
fun getDate(timeMilliSecond: Date, mCommonTimeFormat: String): String {
    return SimpleDateFormat(mCommonTimeFormat, Locale.ENGLISH).format(timeMilliSecond)
}

//get you date with Format by milliseconds
fun getDateByMilliSeconds(timeMilliSecond: Date, mCommonTimeFormat: String): String {
    return SimpleDateFormat(mCommonTimeFormat, Locale.ENGLISH).format(timeMilliSecond)
}

private fun dateDifference(startDate: Date, endDate: Date): Boolean {
    var different = endDate.time - startDate.time
    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24
    val elapsedDays = different / daysInMilli
    different %= daysInMilli
    val elapsedHours = different / hoursInMilli
    different %= hoursInMilli
    val elapsedMinutes = different / minutesInMilli
    return /*elapsedMinutes >= 30L ||*/ elapsedHours > 0L || elapsedDays > 0L
}

//get Date and convert into Time [12 hours with date am and pm]
fun getTime(inputString: String): String? {
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH)
    var date: Date? = null
    try {
        date = dateFormat.parse(inputString)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val calendar = Calendar.getInstance()
    calendar.time = date
    val formatter: DateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return formatter.format(date)
}