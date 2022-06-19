package com.example.contactapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//table in the data base
@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name:String,
    val email:String,
)
