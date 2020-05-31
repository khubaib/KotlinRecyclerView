package com.example.android.kotlinrecyclerview.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SectionDetails (
    @PrimaryKey
    @ColumnInfo(name = "sectionId")
    var sectionId: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String
)