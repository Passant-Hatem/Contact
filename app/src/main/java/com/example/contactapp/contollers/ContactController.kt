package com.example.contactapp.contollers

import androidx.lifecycle.LiveData
import com.example.contactapp.data.Contact
import com.example.contactapp.data.ContactDao
import kotlinx.coroutines.flow.Flow

class ContactController(private val contactDao: ContactDao) {
    val getAllContact: LiveData<List<Contact>> = contactDao.getAllContacts()

    fun addContact(contact: Contact){
        contactDao.addContact(contact)
    }

    fun updateContact(contact: Contact){
        contactDao.updateContact(contact)
    }

    fun searchContact(searchQuery:String):Flow<List<Contact>>{
        return contactDao.searchContact(searchQuery)
    }

    fun deleteContact(contact: Contact){
        contactDao.deleteContact(contact)
    }
}