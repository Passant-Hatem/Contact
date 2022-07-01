package com.example.contactapp.fragments
//TODO fix adding too big image
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.contactapp.R
import com.example.contactapp.data.Contact
import com.example.contactapp.databinding.FragmentCreatContactBinding
import com.example.contactapp.viewmodels.ContactViewModel

class CreateContactFragment : Fragment() {
    private lateinit var binding: FragmentCreatContactBinding
    private lateinit var mContactViewModel:ContactViewModel
    private lateinit var bitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreatContactBinding.inflate(inflater ,container ,false)
        mContactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        (activity as AppCompatActivity).supportActionBar?.title = "Create contact"

         bitmap = BitmapFactory.decodeResource(resources, R.drawable.person)


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
                        .into(binding.addPhotoImg)
                }
            }
        }

        binding.addPhotoImg.setOnClickListener{
            val mIntent = Intent()
            mIntent.type = "image/*"
            mIntent.action = Intent.ACTION_GET_CONTENT
            resultLauncher.launch(mIntent)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_contact_menu, menu)
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

        if(!inputIsEmpty(firstName, lastName, phone ,email)){
            // Create User Object
            val newContact=Contact(
                0,
                firstName,
                lastName,
                phone,
                email,
                bitmap
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

    private fun inputIsEmpty(firstName: String, lastName: String, phone: String ,email:String): Boolean {
        return firstName.isEmpty() && lastName.isEmpty() && phone.isEmpty() && email.isEmpty()
    }
//TODO pop up dialog to cancel add new contact tyr using full screen dialog
  /*  override fun onPause() {
        super.onPause()
        context?.let {
         MaterialAlertDialogBuilder(it)
             .setMessage(resources.getString(R.string.supporting_text))
             .setPositiveButton(resources.getString(R.string.save)) { _, _ ->
                 // Respond to positive button press
                 insertDatToDatabase()
             }
             .setNegativeButton(resources.getString(R.string.discard)) { _, _ ->
                 // Respond to negative button press
             }
             .show()

     }
    }*/
}


