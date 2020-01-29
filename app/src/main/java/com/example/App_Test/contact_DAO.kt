package com.example.App_Test

import androidx.room.*

@Dao
interface contact_DAO {

    @Insert
    fun saveContact(contact:contact_Entity)

    @Query("select * from Contact_Details")
    fun readContact():List<contact_Entity>

    @Query("select * from Contact_Details where id in (:id)")
    fun getContactById(id: Int): contact_Entity

    @Update
    fun updateContact(contact: contact_Entity)

    @Delete
    fun deleteContact(contact: contact_Entity)
}