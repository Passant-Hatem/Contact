package com.example.contactapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contactapp.R
import com.example.contactapp.data.Contact
import com.example.contactapp.databinding.FragmentCreatContactBinding
import com.example.contactapp.viewmodels.ContactViewModle

class CreateContactFragment : Fragment() {
    private lateinit var binding: FragmentCreatContactBinding
    private lateinit var mContactViewModel:ContactViewModle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatContactBinding.inflate(inflater ,container ,false)
        mContactViewModel = ViewModelProvider(this)[ContactViewModle::class.java]
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save) {
            insertDatToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDatToDatabase() {
        val firstName:String = binding.firstName.text.toString()
        val lastName:String = binding.lastName.text.toString()
        val phone:String = binding.phoneNumber.text.toString()
        val email:String = binding.emailAddress.text.toString()
        val imgLink = "image link"

        if(!inputIsEmpty(firstName, lastName, phone ,email ,imgLink)){
            // Create User Object
            val newContact=Contact(
                0,
                firstName,
                lastName,
                phone,
                email,
                imgLink,
            )
            // Add Data to Database
            mContactViewModel.addContact(newContact)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_createContactFragment2_to_contactListFragment)
        }else{
            Toast.makeText(requireContext() ,"please fill all fields" ,Toast.LENGTH_LONG).show()
        }
    }

    private fun inputIsEmpty(firstName: String, lastName: String, phone: String ,email:String ,imgLink:String): Boolean {
        return firstName.isEmpty() && lastName.isEmpty() && phone.isEmpty() && email.isEmpty() && imgLink.isEmpty()
    }
}

