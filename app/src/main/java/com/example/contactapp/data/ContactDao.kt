package com.example.contactapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addContact(vararg contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY id ASC")
    fun getAllContacts(): LiveData<List<Contact>>

}