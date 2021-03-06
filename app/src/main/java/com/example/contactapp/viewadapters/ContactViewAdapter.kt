package com.example.contactapp.viewadapters

import android.app.Activity
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contactapp.R
import com.example.contactapp.data.Contact
import com.example.contactapp.fragments.ContactListFragmentDirections
import kotlinx.coroutines.withContext

class ContactViewAdapter() :
    RecyclerView.Adapter<ContactViewAdapter.ViewHolder>() {

    private var contactData = emptyList<Contact>()

    private var activity = Activity()

     class ViewHolder(view: View):RecyclerView.ViewHolder(view){
         val contactName: TextView = view.findViewById(R.id.contactName)
         val profilePic: ImageView = view.findViewById(R.id.profilePicView)
         val contactRow: View? = view.findViewById(R.id.contactRow)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact, parent, false)
        return ViewHolder(view)
    }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contactName.text = getFullName(contactData[position])
         Glide.with(activity)
             .load(contactData[position].profilePic)
             .circleCrop()
             .into(holder.profilePic)

        //navigate to details fragment
         holder.contactRow?.setOnClickListener{
           val action = ContactListFragmentDirections.actionContactListFragmentToContactDetailsFragment(contactData[position])
           holder.itemView.findNavController().navigate(action)
         }

     }

     private fun getFullName(contact: Contact): CharSequence? {
         val fullName = StringBuffer()
         fullName.append(contact.first_name)
         fullName.append(' ')
         fullName.append(contact.last_name)
         return fullName
     }

     override fun getItemCount(): Int {
       return contactData.size
     }

     fun setData(contacts: List<Contact> ,activity: Activity){
         this.activity = activity
         this.contactData = contacts
         notifyDataSetChanged()
     }
 }