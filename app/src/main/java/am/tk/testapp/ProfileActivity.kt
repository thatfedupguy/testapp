package am.tk.testapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    private val TAG = "ProfileActivity"
    private var mDatabaseReference : DatabaseReference?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val intent = intent
        var number : String = intent.getStringExtra("mobile")
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
        Log.d(TAG, "Databasse :")
        mDatabaseReference!!.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                val value = p0.getValue(String::class.java)
                if(value.equals(number)){
                    mDatabaseReference!!.child(number).child("visit_count").addChildEventListener(object :
                        ChildEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                        }

                        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                            var count_value = p0.getValue(String::class.java) as Int + 1
                            mDatabaseReference!!.child(number).child("visit_count").setValue(count_value)
                        }

                        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                            //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onChildRemoved(p0: DataSnapshot) {
                            //To change body of created functions use File | Settings | File Templates.
                        }
                    })

                }

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }
}
