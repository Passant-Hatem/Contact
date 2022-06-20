package com.example.contactapp.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.data.Contact
import java.util.ArrayList

 class ContactViewAdapter(private val contactData: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactViewAdapter.ViewHolder>() {

     class ViewHolder(view: View):RecyclerView.ViewHolder(view){
         val contactName: TextView = view.findViewById(R.id.contactName)
         val alphabetOrderView: TextView = view.findViewById(R.id.alphabetOrderView)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact, parent, false)
        return ViewHolder(view)
    }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.alphabetOrderView.text
         holder.contactName.text = contactData[position].name
     }

     override fun getItemCount(): Int {
       return contactData.size
     }
 }