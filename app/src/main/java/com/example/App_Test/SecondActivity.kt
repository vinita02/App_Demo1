package com.example.App_Test

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.row_contact.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class SecondActivity : AppCompatActivity() {

     var name:String? = null
    var number:String? = null
    val SELECTIMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var db = Room.databaseBuilder(applicationContext,AppDB::class.java,"Contact_Details").build()

        image_sencond_activity.setOnClickListener(){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECTIMAGE)
        }


        btn_save.setOnClickListener(){

             name = et_Name.text.toString()
             number = et_Number.text.toString()

            var contact = contact_Entity(0,name.toString(),number!!.toInt())


            Thread{


                db.contact_dao().saveContact(contact)

                /* db.contact_dao().readContact().forEach(){
                     Log.i("Fetch Records", "Id:  : ${it.id}")
                     Log.i("Fetch Records", "Name:  : ${it.name}")
                     Log.i("Fetech Recored","Dept: :${it.number}")
                 }*/

            }.start()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }



    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECTIMAGE)
            { val selectedImageUri = data!!.getData()
                image_sencond_activity.setImageURI(selectedImageUri)
            }

        }

    }
}
