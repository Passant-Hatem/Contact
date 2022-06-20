package com.example.contactapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.contactapp.data.Contact
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.viewmodels.ContactViewModle

class ContactListFragment : Fragment() {
    private lateinit var mContactViewModle: ContactViewModle
    private lateinit var binding: FragmentContactListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactListBinding.inflate(inflater, container, false)

        mContactViewModle = ViewModelProvider(this)[ContactViewModle::class.java]

        binding.addNewContactButton.setOnClickListener{insertDataToDB()}

        return binding.root
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