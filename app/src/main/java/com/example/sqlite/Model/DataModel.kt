package com.example.sqlite.Model

import android.content.Context
import com.example.sqlite.DataBase.ContactDBHelper

class DataModel(private val context: Context) {

    companion object {
        private var instance: Any? = null

        fun getInstance(context: Context): DataModel {
            if (instance == null) {
                instance = DataModel(context)
            }
            return instance as DataModel
        }
    }
        fun addContact(contact: Contact) {

            val dbHelper = ContactDBHelper.getInstance(context)
            dbHelper.add(contact)
        }

        fun getContactList(): List<Contact> {
            val dbHelper = ContactDBHelper.getInstance(context)
            return dbHelper.getDataList()
        }

        fun deleteContact(contact: Contact) {
            val dbHelper = ContactDBHelper.getInstance(context)
            dbHelper.delete(contact)
        }

        fun updateContact(contact: Contact) {
            val dbHelper = ContactDBHelper.getInstance(context)
            dbHelper.update(contact)
        }
    }