package com.example.contactapp.fragments
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.contactapp.R
import com.example.contactapp.data.Contact
import com.example.contactapp.databinding.FragmentUpdateBinding
import com.example.contactapp.viewmodels.ContactViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mContactViewModel: ContactViewModel
    private lateinit var bitmap: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Edit contact"


        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    context?.let {
                        MaterialAlertDialogBuilder(it)
                            .setTitle(resources.getString(R.string.supporting_text))
                            .setNeutralButton(resources.getString(R.string.cancle)) { _, _ ->
                                // Respond to neutral button press
                            }
                            .setNegativeButton(resources.getString(R.string.discard)) { _, _ ->
                                // Respond to negative button press
                                val action = UpdateFragmentDirections.actionUpdateFragmentToContactDetailsFragment(args.contactToBeUpdate)
                                findNavController().navigate(action)
                            }
                            .setPositiveButton(resources.getString(R.string.save)) {_, _ ->
                                // Respond to positive button press
                                updateContact()
                            }
                            .show()
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding = FragmentUpdateBinding.inflate(inflater ,container ,false)
        binding.updateFirstName.append(args.contactToBeUpdate.first_name)
        binding.updateLastName.append(args.contactToBeUpdate.last_name)
        binding.updatePhoneNumber.append(args.contactToBeUpdate.phone)
        binding.updateEmailAddress.append(args.contactToBeUpdate.email)
        Glide.with(this)
            .load(args.contactToBeUpdate.profilePic)
            .circleCrop()
            .into(binding.updatePhotoImg)

        mContactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        bitmap = args.contactToBeUpdate.profilePic

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                if (data != null) {
                    //TODO fix deprecate getBitmap method
                    bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver  ,data.data)

                    Glide.with(this)
                        .load(bitmap)
                        .circleCrop()
                        .into(binding.updatePhotoImg)
                }
            }
        }

        binding.updatePhotoImg.setOnClickListener{
            if(bitmap.sameAs(BitmapFactory.decodeResource(resources, R.drawable.person))){
                context?.let {
                    MaterialAlertDialogBuilder(it)
                        .setMessage(resources.getString(R.string.change_photo))
                        .setNeutralButton(resources.getString(R.string.cancle)) { _, _ ->
                            //Respond to natural button
                            MaterialAlertDialogBuilder(it).setCancelable(true)
                        }
                        .setPositiveButton(resources.getString(R.string.change)) { _, _ ->
                            // Respond to positive button pressed
                            MaterialAlertDialogBuilder(it)
                            val mIntent = Intent()
                            mIntent.type = "image/*"
                            mIntent.action = Intent.ACTION_GET_CONTENT
                            resultLauncher.launch(mIntent)
                        }
                        .show()
                }
            }
            else {
                context?.let {
                    MaterialAlertDialogBuilder(it)
                        .setMessage(resources.getString(R.string.change_photo))
                        .setNeutralButton(resources.getString(R.string.cancle)) { _, _ ->
                            //Respond to natural button
                            MaterialAlertDialogBuilder(it).setCancelable(true)
                        }
                        .setPositiveButton(resources.getString(R.string.change)) { _, _ ->
                            // Respond to positive button pressed
                            MaterialAlertDialogBuilder(it)
                            val mIntent = Intent()
                            mIntent.type = "image/*"
                            mIntent.action = Intent.ACTION_GET_CONTENT
                            resultLauncher.launch(mIntent)
                        }
                        .setNegativeButton(resources.getString(R.string.delete)) { _, _ ->
                            // Respond to negative button press
                            bitmap = BitmapFactory.decodeResource(resources, R.drawable.person)
                            Glide.with(this)
                                .load(bitmap)
                                .circleCrop()
                                .into(binding.updatePhotoImg)
                        }
                        .show()
                }
            }
        }

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

        if(!inputIsEmpty(firstName, lastName, phone ,email)){
            if (checkEmail(email)){
                // Create User Object
                val newContact= Contact(
                    args.contactToBeUpdate.id,
                    firstName,
                    lastName,
                    phone,
                    email,
                    bitmap
                )
                // Add Data to Database
                mContactViewModel.updateContact(newContact)
                Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
                // Navigate Back
                val action = UpdateFragmentDirections.actionUpdateFragmentToContactDetailsFragment(newContact)
                findNavController().navigate(action)

            }else{
                Toast.makeText(requireContext() ,"please enter an correct email" ,Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(requireContext() ,"please fill all fields" , Toast.LENGTH_LONG).show()
        }
    }

    private fun inputIsEmpty(firstName: String, lastName: String, phone: String ,email:String ): Boolean {
        return firstName.isEmpty() && lastName.isEmpty() && phone.isEmpty() && email.isEmpty()
    }

    private fun checkEmail(email:String):Boolean{
        return email.contains('@')
    }
}