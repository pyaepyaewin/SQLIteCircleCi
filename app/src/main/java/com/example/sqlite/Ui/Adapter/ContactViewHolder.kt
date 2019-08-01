package com.example.sqlite.Ui.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.Model.Contact
import kotlinx.android.synthetic.main.layoutcontact.view.*

class ContactViewHolder(
    private val view: View,
    private val onClick: (contact: Contact) -> Unit,
    private val onLongClick: (contact: Contact) -> Unit
) :
    RecyclerView.ViewHolder(view) {
    fun setData(contact: Contact) {
        view.apply {
            txtText1.text = contact.name
            txtText2.text = contact.phone
            txtText3.text = contact.address
        }
        view.setOnClickListener { onClick(contact) }
        view.setOnLongClickListener {
            onLongClick(contact)
            true
        }
    }
}