package com.example.contactapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.R
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.viewadapters.ContactViewAdapter
import com.example.contactapp.viewmodels.ContactViewModle

class ContactListFragment : Fragment() {
    private lateinit var binding: FragmentContactListBinding
    private lateinit var mContactViewModel: ContactViewModle

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

        // Recyclerview
        val adapter = ContactViewAdapter()
        binding.contacView.adapter = adapter
        binding.contacView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mContactViewModel = ViewModelProvider(this)[ContactViewModle::class.java]
        mContactViewModel.getAllContacts.observe(viewLifecycleOwner) { contact ->
            adapter.setData(contact)
        }

        return binding.root
    }

}