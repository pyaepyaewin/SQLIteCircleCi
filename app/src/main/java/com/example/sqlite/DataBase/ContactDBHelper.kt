package com.example.sqlite.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.sqlite.Model.Contact

class ContactDBHelper(context: Context)
    :SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "contact.db"
        const val DATABASE_VERSION = 1
        const val SQL_CREATE_ENTRIES = "CREATE TABLE ${ContactContract.ContactEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${ContactContract.ContactEntry.COLUMN_NAME_USERNAME} TEXT, " +
                "${ContactContract.ContactEntry.COLUMN_NAME_PHONE_NUMBER} TEXT, " +
                "${ContactContract.ContactEntry.COLUMN_NAME_ADDRESS} TEXT)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ContactContract.ContactEntry.TABLE_NAME}"

        private var instance: ContactDBHelper? = null

        fun getInstance(context: Context): ContactDBHelper {
            if (instance == null) {
                instance = ContactDBHelper(context)
            }
            return instance as ContactDBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun add(contact: Contact) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(ContactContract.ContactEntry.COLUMN_NAME_USERNAME, contact.name)
            put(ContactContract.ContactEntry.COLUMN_NAME_PHONE_NUMBER, contact.phone)
            put(ContactContract.ContactEntry.COLUMN_NAME_ADDRESS, contact.address)
        }
        val newRowId = db.insert(ContactContract.ContactEntry.TABLE_NAME, null, values)
        Log.d("NewRowId", "$newRowId")
    }

    fun getDataList(): List<Contact> {
        val db = this.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID, ContactContract.ContactEntry.COLUMN_NAME_USERNAME,
            ContactContract.ContactEntry.COLUMN_NAME_PHONE_NUMBER, ContactContract.ContactEntry.COLUMN_NAME_ADDRESS
        )
        val sortOrder = "${ContactContract.ContactEntry.COLUMN_NAME_USERNAME} ASC"
        val cursor = db.query(
            ContactContract.ContactEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val contactList = mutableListOf<Contact>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(android.provider.BaseColumns._ID))
                val name =
                    getString(getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_USERNAME))
                val phoneNumber =
                    getString(getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_PHONE_NUMBER))
                val address =
                    getString(getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_ADDRESS))
                contactList.add(Contact(id, name, phoneNumber, address))
            }
        }
        return contactList
    }
    fun delete(contact: Contact) {
        val db = this.writableDatabase
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(contact.id.toString())
        val deletedRow = db.delete(ContactContract.ContactEntry.TABLE_NAME, selection, selectionArgs)
        Log.d("DeletedRow", "$deletedRow")

    }
    fun update(contact: Contact) {
        val db = this.writableDatabase
        val value= ContentValues().apply {
            put(ContactContract.ContactEntry.COLUMN_NAME_USERNAME, contact.name)
            put(ContactContract.ContactEntry.COLUMN_NAME_PHONE_NUMBER, contact.phone)
            put(ContactContract.ContactEntry.COLUMN_NAME_ADDRESS, contact.address)
        }
            val selection = "${BaseColumns._ID} = ?"
            val selectionArgs = arrayOf(contact.id.toString())
            val count = db.update(
                ContactContract.ContactEntry.TABLE_NAME,
                value,
                selection,
                selectionArgs
            )
            Log.d("Number of rows affected", "$count")
        }

    }