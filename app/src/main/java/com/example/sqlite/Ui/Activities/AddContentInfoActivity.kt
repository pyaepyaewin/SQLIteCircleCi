package com.example.sqlite.Ui.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlite.Model.Contact
import com.example.sqlite.Model.DataModel
import com.example.sqlite.R
import kotlinx.android.synthetic.main.activity_add_content_info.*

class AddContentInfoActivity : AppCompatActivity() {

    private lateinit var dataModel: DataModel
    private var isEdit = false
    private var contactData: Contact? = null

    companion object {
        var IE_IS_EDIT = "isEdit"
        var IE_ID = "id"
        var IE_NAME = "name"
        var IE_PHONE = "phone"
        var IE_ADDRESS = "address"

        fun newActivity(
            context: Context,
            isEdit: Boolean,
            id: Int? = null,
            name: String? = null,
            phone: String? = null,
            address: String? = null
        ): Intent {
            val intent = Intent(context, AddContentInfoActivity::class.java)
            intent.putExtra(IE_IS_EDIT, isEdit)
            intent.putExtra(IE_ID, id)
            intent.putExtra(IE_NAME, name)
            intent.putExtra(IE_PHONE, phone)
            intent.putExtra(IE_ADDRESS, address)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_content_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (intent.getBooleanExtra(IE_IS_EDIT, false)) {
            isEdit = true
            val id = intent.getIntExtra(IE_ID, 0)
            val name = intent.getStringExtra(IE_NAME)
            val phone = intent.getStringExtra(IE_PHONE)
            val address = intent.getStringExtra(IE_ADDRESS)
            contactData = Contact(id, name, phone, address)
            edtName.setText(name)
            edtPhone.setText(phone)
            edtAddress.setText(address)
            btnAddContact.text = "Add Content"
        }

        dataModel = DataModel.getInstance(this)

        btnAddContact.setOnClickListener {
            if (edtName.text.isBlank()) {
                edtName.error = "Please enter name"
                return@setOnClickListener
            }
            if (edtPhone.text.isBlank()) {
                edtPhone.error = "Please enter phone number"
                return@setOnClickListener
            }
            if (edtAddress.text.isBlank()) {
                edtAddress.error = "Please enter address"
                return@setOnClickListener
            }
            val name = edtName.text.toString()
            val phone = edtPhone.text.toString()
            val address = edtAddress.text.toString()

            if (isEdit) {
                contactData = Contact(contactData?.id, name, phone, address)
                dataModel.updateContact(contact = contactData!!)
            } else {
                contactData = Contact(null, name, phone, address)
                dataModel.addContact(contact = contactData!!)
            }
            finish()
        }

    }
}

