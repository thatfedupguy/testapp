package am.tk.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getDataDirectory
import android.widget.Button
import com.camerakit.CameraKitView
import android.os.Environment.getExternalStorageDirectory
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.google.firebase.database.DatabaseReference
import java.io.File
import java.io.FileOutputStream
import com.google.firebase.database.FirebaseDatabase




class CaptureActivity : AppCompatActivity() {
    var database : FirebaseDatabase ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

    }


}
