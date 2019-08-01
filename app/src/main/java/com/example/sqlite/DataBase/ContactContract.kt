package com.example.sqlite.DataBase

import android.provider.BaseColumns

object ContactContract {
    object ContactEntry: BaseColumns {
        const val TABLE_NAME = "contact"
        const val COLUMN_NAME_USERNAME = "name"
        const val COLUMN_NAME_PHONE_NUMBER = "phone"
        const val COLUMN_NAME_ADDRESS = "address"
    }
}