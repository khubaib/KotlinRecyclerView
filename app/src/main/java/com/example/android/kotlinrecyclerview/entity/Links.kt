package com.example.android.kotlinrecyclerview.entity


import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


data class Links(
    @PrimaryKey(autoGenerate = true)
    val idd: Long,
    @SerializedName("viaplay:sections")
    @TypeConverters(DataConverter::class)
    var viaplaySections: List<ViaplaySection>
){
}


class DataConverter {
    @TypeConverter
    fun fromSectionsList(countryLang: List<ViaplaySection?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ViaplaySection?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toSectionsList(countryLangString: String?): List<ViaplaySection>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<ViaplaySection?>?>() {}.type
        return gson.fromJson(countryLangString, type)
    }
}