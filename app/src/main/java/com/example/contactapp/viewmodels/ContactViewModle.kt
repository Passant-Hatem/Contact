package com.example.contactapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.contactapp.contollers.ContactController
import com.example.contactapp.data.Contact
import com.example.contactapp.data.ContactDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModle(application: Application):AndroidViewModel(application){
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
}