package com.example.android.kotlinrecyclerview.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class ViaplaySection(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("href")
    @Expose
    var href: String,

    @SerializedName("type")
    @Expose
    var type: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("templated")
    @Expose
    var templated: Boolean

) :  Parcelable