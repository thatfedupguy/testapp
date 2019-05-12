package am.tk.testapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_verify.*
import java.util.concurrent.TimeUnit


class VerifyActivity : AppCompatActivity() {
    private val TAG = "VerifyActivity"
    private var mVerificationId : String = ""
    private var mAuth : FirebaseAuth ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        mAuth = FirebaseAuth.getInstance()

        val intent = intent
        var number : String = intent.getStringExtra("mobile")

        Log.d(TAG, "send :")
        sendVerificationCode(number)


    }

    private fun sendVerificationCode(number: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91"+number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallbacks
        )

    }
    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
            //Getting the code sent by SMS
            val code = phoneAuthCredential.smsCode

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                et_code.text(code)
                //verifying the code
                verifyVerificationCode(code)
            }
        }

        override fun onVerificationFailed(p0 : FirebaseException) {
            Toast.makeText(this@VerifyActivity, p0.message, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(s: String?, forceResendingToken: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(s, forceResendingToken)
            mVerificationId = s!!
        }
    }
    private fun verifyVerificationCode(otp: String) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId, otp)

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this@VerifyActivity,
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        //verification successful we will start the profile activity
                        val intent = Intent(this@VerifyActivity, ProfileActivity::class.java)
                        intent.putExtra("mobile", credential.provider)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)

                    } else {

                        //verification unsuccessful.. display an error message

                        var message = "Somthing is wrong, we will fix it soon..."

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered..."
                        }

                        val snackbar = Snackbar.make(findViewById(R.id.coordinator), message, Snackbar.LENGTH_LONG)
                        snackbar.setAction("Dismiss", object : View.OnClickListener {
                            override fun onClick(v: View) {

                            }
                        })
                        snackbar.show()
                    }
                })
    }
}

private operator fun Editable.invoke(code: String) {

}



