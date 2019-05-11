package am.tk.testapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import java.io.File


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var mStorage : StorageReference ?= null
    val CAMERA_REQUEST_CODE  = 1
    var mProgressDialog : ProgressDialog ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mStorage = FirebaseStorage.getInstance().getReference()
        mProgressDialog = ProgressDialog(this)



        visit_button.setOnClickListener(View.OnClickListener {
            val mobile = etvar_number.getText().toString().trim()

            if (mobile.isEmpty() || mobile.length < 10) {
                etvar_number.setError("Enter a valid mobile")
                etvar_number.requestFocus()
                return@OnClickListener
            }

            val intent = Intent(this@MainActivity, VerifyActivity::class.java)
            intent.putExtra("mobile", mobile)
            startActivity(intent)
        })
        image_button.setOnClickListener {
            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(captureIntent, CAMERA_REQUEST_CODE )
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK ){
           // mProgressDialog!!.setMessage("Uploading Image")
            //mProgressDialog!!.show()
            Log.d(TAG, "Uploading image: ")
            profile_image.setImageBitmap(data!!.extras.get("data") as Bitmap)
            if(data!=null ){
                data.data?.also { uri ->
                    var filePath: StorageReference = mStorage!!.child("Photos").child(uri.lastPathSegment)
                    filePath.putFile(uri).addOnSuccessListener { taskSnapshot ->
                        mProgressDialog!!.dismiss()

                        Toast.makeText(this, "Uploading Finished", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }






}
