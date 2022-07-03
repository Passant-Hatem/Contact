package com.example.contactapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactapp.contollers.ContactController
import com.example.contactapp.data.Contact
import com.example.contactapp.data.ContactDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application):AndroidViewModel(application){
    val getAllContacts: LiveData<List<Contact>>
    private val contactController:ContactController
    init {
        val contactDao = ContactDataBase.getDatabase(
            application
        ).contactDao()
        contactController = ContactController(contactDao)
        getAllContacts = contactController.getAllContact
    }

    fun addContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO){
            contactController.addContact(contact)
        }
    }

    fun updateContact(contact: Contact){
        viewModelScope.launch (Dispatchers.IO){
           contactController.updateContact(contact)
        }
    }

    fun searchContact(searchQuery: String): LiveData<List<Contact>> {
        return contactController.searchContact(searchQuery).asLiveData()
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch (Dispatchers.IO){
            contactController.deleteContact(contact)
        }
    }

}