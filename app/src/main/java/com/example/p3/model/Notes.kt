package com.example.p3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val content : String
)
