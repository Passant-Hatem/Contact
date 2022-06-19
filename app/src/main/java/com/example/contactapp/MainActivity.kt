package com.example.contactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.data.Contact
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.viewmodels.ContactViewModle
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var mContactViewModle: ContactViewModle
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mContactViewModle = ViewModelProvider(this)[ContactViewModle::class.java]

        binding.addNewContactButton.setOnClickListener{insertDataToDB()}

        val layoutManager = LinearLayoutManager(this)

        binding.contacView.layoutManager = layoutManager
    }

    private fun insertDataToDB(){
        val contact = Contact(
            0 ,
            "passant" ,
            "passnat@gmail.com"
        )
        mContactViewModle.addContact(contact)
    }
}