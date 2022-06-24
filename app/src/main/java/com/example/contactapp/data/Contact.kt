package com.example.contactapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//table in the data base
@Parcelize
@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val first_name:String,
    val last_name:String,
    val phone:String,
    val email:String,
    val img_link:String
):Parcelable
