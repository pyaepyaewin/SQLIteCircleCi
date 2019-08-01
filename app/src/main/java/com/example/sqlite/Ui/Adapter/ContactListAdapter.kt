package com.example.sqlite.Ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.Model.Contact
import com.example.sqlite.R

class ContactListAdapter(private val onClick: (contact: Contact) -> Unit, private val onLongClick: (contact: Contact) -> Unit) :
    RecyclerView.Adapter<ContactViewHolder>() {
    private var contactDataList = emptyList<Contact>()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContactViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.layoutcontact, p0, false)
        return ContactViewHolder(view = view, onClick = onClick, onLongClick = onLongClick)
    }

    override fun getItemCount(): Int {
        return contactDataList.size
    }

    override fun onBindViewHolder(viewholder: ContactViewHolder, position: Int) {
        viewholder.setData(contactDataList[position])
    }

    fun setContactList(contactList: List<Contact>) {
        this.contactDataList = contactList
        notifyDataSetChanged()
    }
}