package com.ahmadrd.movieexplorer.core.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object FormatTime {
    fun formatRelativeTime(dateString: String?): String? {
        return try {
            dateString?.let { dateStr ->
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val date = sdf.parse(dateStr)

                date?.let {
                    val now = Date()
                    val diffInMillis = now.time - it.time
                    val diffInSeconds = diffInMillis / 1000
                    val diffInMinutes = diffInSeconds / 60
                    val diffInHours = diffInMinutes / 60
                    val diffInDays = diffInHours / 24

                    when {
                        diffInSeconds < 60 -> "Just Now"
                        diffInMinutes < 60 -> "$diffInMinutes minutes ago"
                        diffInHours < 24 -> "$diffInHours hours ago"
                        diffInDays < 7 -> "$diffInDays days ago"
                        else -> {
                            // Full format for dates more than 6 days
                            val fullDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                            fullDateFormat.format(it)
                        }
                    }
                } ?: dateStr // Return original if failed parsing
            }
        } catch (_: Exception) {
            dateString // Return original if error
        }
    }

    fun formatRelativeTimeFromDate(dateString: String?): String? {
        return try {
            dateString?.let { dateStr ->
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = sdf.parse(dateStr)

                date?.let {
                    val now = Date()
                    val calendar = Calendar.getInstance()

                    // Set the time to midnight for accurate day comparison
                    calendar.time = now
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    val todayMidnight = calendar.time

                    calendar.time = it
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    val dateMidnight = calendar.time

                    val diffInMillis = todayMidnight.time - dateMidnight.time
                    val diffInDays = diffInMillis / (1000 * 60 * 60 * 24)

                    when (diffInDays) {
                        0L -> "Today"
                        1L -> "Yesterday"
                        in 2..6 -> "$diffInDays days ago"
                        else -> {
                            // Full format for dates more than 6 days
                            val fullDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                            fullDateFormat.format(it)
                        }
                    }
                } ?: dateStr // Return original if failed parsing
            }
        } catch (_: Exception) {
            dateString // Return original if error
        }
    }
}