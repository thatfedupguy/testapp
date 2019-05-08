package am.tk.testapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image_button.setOnClickListener {
            val captureIntent = Intent(this@MainActivity, Capture2Activity::class.java)
            startActivity(captureIntent)
            finish()
        }

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


    }
}
