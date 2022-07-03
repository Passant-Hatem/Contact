package com.example.contactapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.R
import com.example.contactapp.databinding.FragmentContactListBinding
import com.example.contactapp.viewadapters.ContactViewAdapter
import com.example.contactapp.viewmodels.ContactViewModel
import android.view.Menu

class ContactListFragment : Fragment() , SearchView.OnQueryTextListener{
    private lateinit var binding: FragmentContactListBinding
    private lateinit var mContactViewModel: ContactViewModel
    private lateinit var adapter:ContactViewAdapter
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
        adapter = ContactViewAdapter()
        binding.contactView.adapter = adapter
        binding.contactView.layoutManager = LinearLayoutManager(requireContext())
        // UserViewModel
        mContactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        mContactViewModel.getAllContacts.observe(viewLifecycleOwner) { contact ->
            adapter.setData(contact , activity as AppCompatActivity)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
           searchForContact(newText)
        }
        return true
    }

    private fun searchForContact(text: String) {
        val searchQuery = "%$text%"
        mContactViewModel.searchContact(searchQuery).observe(viewLifecycleOwner){
            adapter.setData(it , activity as AppCompatActivity)
        }
    }

}