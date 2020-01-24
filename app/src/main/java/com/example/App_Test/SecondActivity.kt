package com.example.App_Test

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import java.io.FileOutputStream

class SecondActivity : AppCompatActivity() {

     var name:String? = null
    var number:String? = null
    val SELECTIMAGE = 100
    var bitmap: Bitmap? = null
    var image:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var db = Room.databaseBuilder(applicationContext,AppDB::class.java,"Contact_Details").build()

        image_sencond_activity.setOnClickListener(){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECTIMAGE)
           /* bitmap = image_sencond_activity*/
        }


        btn_save.setOnClickListener(){

             name = et_Name.text.toString()
             number = et_Number.text.toString()
            var img = image

            var contact = contact_Entity(0,name.toString(),number!!.toString(),img!!)


            Thread{
                db.contact_dao().saveContact(contact)

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
            {
               /* val selectedImageUri = data!!.getData()
                image_sencond_activity.setImageURI(selectedImageUri)*/

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
