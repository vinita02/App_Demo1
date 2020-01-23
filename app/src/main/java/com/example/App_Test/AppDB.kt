package com.example.App_Test

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(contact_Entity::class)],version = 2)
abstract class AppDB:RoomDatabase() {

    abstract fun contact_dao():contact_DAO
}