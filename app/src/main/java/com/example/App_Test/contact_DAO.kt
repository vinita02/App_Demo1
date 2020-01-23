package com.example.App_Test

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface contact_DAO {

    @Insert
    fun saveContact(contact:contact_Entity)

    @Query("select * from Contact_Details")
    fun readContact():List<contact_Entity>
}