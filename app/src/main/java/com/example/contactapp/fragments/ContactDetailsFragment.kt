package com.example.contactapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contactapp.R
import com.example.contactapp.data.Contact
import com.example.contactapp.databinding.FragmentContactDetailsBinding

class ContactDetailsFragment : Fragment() {
    private  lateinit var binding: FragmentContactDetailsBinding
    private val detailArgs by navArgs<ContactDetailsFragmentArgs>()
    private val updateArgs by navArgs<UpdateFragmentArgs>()

    private lateinit var contact: Contact

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setting data to ui
        binding = FragmentContactDetailsBinding.inflate(inflater ,container ,false)

        contact = if (detailArgs.selectedContact != null) detailArgs.selectedContact
        else updateArgs.contactToBeUpdate

        binding.fullName.text = getFullName(contact)
        binding.phoneNumberDetail.text = contact.phone
        binding.emialDetail.text = contact.email

        binding.emailContact.setOnClickListener{
            emailContact(contact.email)
        }

        binding.callContact.setOnClickListener {
            callContact(contact.phone)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun getFullName(contact: Contact): CharSequence {
        val fullName = StringBuffer()
        fullName.append(contact.first_name)
        fullName.append(' ')
        fullName.append(contact.last_name)
        return fullName
    }

    private fun callContact(phone: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$phone")
        startActivity(dialIntent)
    }

    //TODO fix it
    private fun emailContact(email: String) {
//        val mIntent = Intent(Intent.ACTION_SEND)
//        mIntent.data = Uri.parse("mailto:")
//        mIntent.type = "text/plain"
//        mIntent.putExtra(Intent.EXTRA_EMAIL, email)
//        startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contact_details_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> editContact()
            R.id.delete -> deleteContact()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteContact() {
        Log.e("passant" ,"delete")
    }

    private fun editContact() {
     val action = ContactDetailsFragmentDirections.actionContactDetailsFragmentToUpdateFragment(detailArgs.selectedContact)
     findNavController().navigate(action)
    }


}