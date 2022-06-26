package com.example.contactapp.fragments
//TODO when press back button it back to update fragment not list fragment
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contactapp.R
import com.example.contactapp.data.Contact
import com.example.contactapp.databinding.FragmentUpdateBinding
import com.example.contactapp.viewmodels.ContactViewModel

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mContactViewModel: ContactViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater ,container ,false)
        binding.updateFirstName.append(args.contactToBeUpdate.first_name)
        binding.updateLastName.append(args.contactToBeUpdate.last_name)
        binding.updatePhoneNumber.append(args.contactToBeUpdate.phone)
        binding.updateEmailAddress.append(args.contactToBeUpdate.email)

        mContactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        setHasOptionsMenu(true)

        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_contact_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.save){
            updateContact()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateContact() {
        val firstName:String = binding.updateFirstName.text.toString()
        val lastName:String = binding.updateLastName.text.toString()
        val phone:String = binding.updatePhoneNumber.text.toString()
        val email:String = binding.updateEmailAddress.text.toString()
        val imgLink = "image link"

        if(!inputIsEmpty(firstName, lastName, phone ,email ,imgLink)){
            // Create User Object
            val newContact= Contact(
                args.contactToBeUpdate.id,
                firstName,
                lastName,
                phone,
                email,
                imgLink,
            )
            // Add Data to Database
            mContactViewModel.updateContact(newContact)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            // Navigate Back
            val action = UpdateFragmentDirections.actionUpdateFragmentToContactDetailsFragment(newContact)
            findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext() ,"please fill all fields" , Toast.LENGTH_LONG).show()
        }
    }

    private fun inputIsEmpty(firstName: String, lastName: String, phone: String ,email:String ,imgLink:String): Boolean {
        return firstName.isEmpty() && lastName.isEmpty() && phone.isEmpty() && email.isEmpty() && imgLink.isEmpty()
    }
}