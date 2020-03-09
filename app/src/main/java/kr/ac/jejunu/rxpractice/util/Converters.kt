package kr.ac.jejunu.rxpractice.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.ac.jejunu.rxpractice.model.Description
import java.sql.Date
import java.util.*

object Converters {
    val gson = Gson()
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Calendar? = value?.let { value ->
        GregorianCalendar().also { calendar ->
            calendar.timeInMillis = value
        }
    }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(timestamp: Calendar?): Long? = timestamp?.timeInMillis

    @TypeConverter
    @JvmStatic
    fun fromString(content: String): List<Description> {
        if (content.isNullOrEmpty()) {
            return emptyList()
        }
        return gson.fromJson<List<Description>>(content)
    }

    @TypeConverter
    @JvmStatic
    fun toList(list: List<Description>): String? {
        if (list.isEmpty()) {
            return null
        }
        return gson.toJson(list)
    }

    inline fun <reified T> Gson.fromJson(json: String) =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)
}