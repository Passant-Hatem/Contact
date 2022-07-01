package com.example.contactapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.R
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.viewadapters.ContactViewAdapter
import com.example.contactapp.viewmodels.ContactViewModel

class ContactListFragment : Fragment() {
    private lateinit var binding: FragmentContactListBinding
    private lateinit var mContactViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactListBinding.inflate(inflater, container, false)

        //add new contact floating button
        binding.addNewContactButton.setOnClickListener{
            findNavController().navigate(R.id.action_contactListFragment_to_createContactFragment2)
        }

        (activity as AppCompatActivity).supportActionBar?.title = "Contacts"

        // Recyclerview
        val adapter = ContactViewAdapter()
        binding.contactView.adapter = adapter
        binding.contactView.layoutManager = LinearLayoutManager(requireContext())
        // UserViewModel
        mContactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        mContactViewModel.getAllContacts.observe(viewLifecycleOwner) { contact ->
            adapter.setData(contact , activity as AppCompatActivity)
        }

        return binding.root
    }

}