package com.example.App_Test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact_Details")
data class contact_Entity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "Name")
    var name: String,

    @ColumnInfo(name = "Number")
    var number: String,
    @ColumnInfo(name = "IMAGE") var img: String?

    )