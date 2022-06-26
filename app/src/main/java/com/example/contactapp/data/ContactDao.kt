package com.example.contactapp.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addContact(vararg contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY first_name ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Delete
    fun deleteContact(contact: Contact)
}