package com.example.contactapp.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.data.Contact
import java.util.ArrayList

 class ContactViewAdapter() :
    RecyclerView.Adapter<ContactViewAdapter.ViewHolder>() {

    private var contactData = emptyList<Contact>()

     class ViewHolder(view: View):RecyclerView.ViewHolder(view){
         val contactName: TextView = view.findViewById(R.id.contactName)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact, parent, false)
        return ViewHolder(view)
    }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contactName.text = getFullName(contactData[position])
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

     fun setData(contacts: List<Contact>){
         this.contactData = contacts
         notifyDataSetChanged()
     }
 }