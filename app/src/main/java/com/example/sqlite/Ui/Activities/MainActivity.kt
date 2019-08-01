package com.example.sqlite.Ui.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlite.Model.Contact
import com.example.sqlite.Model.DataModel
import com.example.sqlite.R
import com.example.sqlite.Ui.Adapter.ContactListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mAdapter: ContactListAdapter by lazy { ContactListAdapter(this::onClickItem, this::onLongClickItem) }
    private lateinit var dataModel: DataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvContact.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        dataModel = DataModel.getInstance(this)
    }

    override fun onResume() {
        super.onResume()
        mAdapter.setContactList(dataModel.getContactList())
    }

    private fun onClickItem(contact: Contact) {
        val intent = AddContentInfoActivity.newActivity(
            this,
            isEdit = true,
            id = contact.id,
            name = contact.name,
            phone = contact.phone,
            address = contact.address
        )
        startActivity(intent)
    }

    private fun onLongClickItem(contact: Contact) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure to deleteContact?")
            .setPositiveButton("OK") { _, _ ->
                dataModel.deleteContact(contact)
                mAdapter.setContactList(dataModel.getContactList())
                Toast.makeText(applicationContext, "Successfully deleted ${contact.name}", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuAdd) {

            val intent = AddContentInfoActivity.newActivity(this, isEdit = false)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
