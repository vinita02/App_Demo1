package com.example.App_Test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter:ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_floating.setOnClickListener(){
            intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }

        myAdapter = ContactsAdapter(this, mutableListOf())
        recycler.adapter = myAdapter


       var thread = Thread{

           var db = Room.databaseBuilder(applicationContext,AppDB::class.java,"Contact_Details").build()

           val address = db.contact_dao().readContact()
           runOnUiThread {
               // Stuff that updates the UI
               myAdapter.addAll(address)

               Log.d("Tag","list"+address)
           }

        }
        thread.start()
    }
}
