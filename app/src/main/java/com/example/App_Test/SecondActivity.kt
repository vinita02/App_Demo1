package com.example.App_Test

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_second.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class SecondActivity : AppCompatActivity() {


    var name:String? = null
    var number:String? = null
    val SELECTIMAGE = 100
    var bitmap: Bitmap? = null
    var image:String? = null
    var currentContact: Int? = null
    var contactId: contact_Entity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var db = Room.databaseBuilder(applicationContext,AppDB::class.java,"Contact_Details").build()

        currentContact = intent.getIntExtra("id", -1)
        if (currentContact != -1) {
            setTitle("Edit Contact")

         Thread{
             contactId = db.contact_dao().getContactById(currentContact!!)
             et_Name.setText(contactId!!.name)
             et_Number.setText(contactId!!.number)

         }.start()

        } else {
            setTitle("Add Contact")
            invalidateOptionsMenu()
        }

        image_sencond_activity.setOnClickListener(){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECTIMAGE)
           /* bitmap = image_sencond_activity*/
        }


        btn_save.setOnClickListener(){

            if(currentContact == -1){

                name = et_Name.text.toString()
                number = et_Number.text.toString()
                var img = image

                var contact = contact_Entity(0,name.toString(),number.toString(),img.toString())


                Thread{
                    db.contact_dao().saveContact(contact)


                }.start()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                var nameContact = et_Name.text.toString()
                var numberContact = et_Number.text.toString()
                var contact = contact_Entity(contactId!!.id, nameContact, numberContact,null)
                Thread{
                    db.contact_dao().updateContact(contact)
                }.start()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
/*
             name = et_Name.text.toString()
             number = et_Number.text.toString()
            var img = image

            var contact = contact_Entity(0,name.toString(),number.toString(),img.toString())


            Thread{
                db.contact_dao().saveContact(contact)


            }.start()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
        }

        btn_delete.setOnClickListener(){

            Thread{
                db.contact_dao().deleteContact(contactId!!)

            }.start()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }



    }

   /* fun update(){
       btn_save.setOnClickListener(){
           if(currentContact == -1){

           }
       }
    }*/

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECTIMAGE)
            {
                val selectedImageUri = data!!.data
                image_sencond_activity.setImageURI(selectedImageUri)
                val bitmap = getContactBitmapFromURI(this, Uri.parse(selectedImageUri.toString()))
                val folderPath = saveBitmapIntoSDCardImage(this, bitmap!!)
                image = folderPath.toString()
                image_sencond_activity.setImageBitmap(bitmap)
            }

        }

    }

    fun getContactBitmapFromURI(context: Context, uri: Uri): Bitmap? {
        try {
            val input = context.getContentResolver().openInputStream(uri) ?: return null
            return BitmapFactory.decodeStream(input)
        } catch (e: FileNotFoundException) {

        }

        return null

    }

    fun saveBitmapIntoSDCardImage(context: Context, finalBitmap: Bitmap): File {
        val mFolder = File("${getExternalFilesDir(null)?.absolutePath}/sample")
        val imgFile = File(mFolder.absolutePath + "/${System.currentTimeMillis()}.png")
        if (!mFolder.exists()) {
            mFolder.mkdir()
        }
        if (!imgFile.exists()) {
            imgFile.createNewFile()
        }

        try {
            val out = FileOutputStream(imgFile)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
           // Log.d("Check ","Img"+imgFile)
            out.flush()
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return imgFile
    }

}
