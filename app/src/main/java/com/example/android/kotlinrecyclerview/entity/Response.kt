package com.example.android.kotlinrecyclerview.entity


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
data class Response (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
   /* @Json(name = "title")
    val title: String,
    @Json(name="description")
    val description: String,*/
    @SerializedName("_links")
    @Expose
    @Embedded
    val links: Links
    /*@Json(name = "type")
    val type: String*/
)

{

    /*constructor(): this(
        0,
        "",
        "",
        null,
        ""
    )*/
}

