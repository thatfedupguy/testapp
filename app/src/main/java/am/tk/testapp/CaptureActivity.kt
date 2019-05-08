package am.tk.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import com.camerakit.CameraKitView
import android.os.Environment.getExternalStorageDirectory
import android.support.annotation.NonNull
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileOutputStream


class CaptureActivity : AppCompatActivity() {
    private var cameraKitView: CameraKitView? = null
    private var photoButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)
        cameraKitView = findViewById(R.id.camera);

        photoButton = findViewById(R.id.photoButton);
        photoButton?.setOnClickListener(photoOnClickListener);
    }
    override fun onResume() {
        super.onResume()
        cameraKitView?.onResume()
    }

    override fun onPause() {
        cameraKitView?.onPause()
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraKitView?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private val photoOnClickListener = View.OnClickListener {
        cameraKitView?.captureImage(CameraKitView.ImageCallback { cameraKitView, photo ->
            val savedPhoto = File(Environment.getExternalStorageDirectory(), "photo.jpg")
            try {
                val outputStream = FileOutputStream(savedPhoto.path)
                outputStream.write(photo)
                outputStream.close()
            } catch (e: java.io.IOException) {
                e.printStackTrace()
                Log.e("CKDemo", "Exception in photo callback")
            }
        })
    }

}
