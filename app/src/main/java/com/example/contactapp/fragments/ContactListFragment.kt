package com.example.contactapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.contactapp.R
import com.example.contactapp.databinding.FragmentContactListBinding

class ContactListFragment : Fragment() {
    private lateinit var binding: FragmentContactListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        binding.addNewContactButton.setOnClickListener{
            findNavController().navigate(R.id.action_contactListFragment_to_createContactFragment2)
        }

        return binding.root
    }

}