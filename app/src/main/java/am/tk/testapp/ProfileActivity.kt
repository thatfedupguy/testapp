package am.tk.testapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import am.tk.testapp.Visitors as Visitors1

class ProfileActivity : AppCompatActivity() {
    private val TAG = "ProfileActivity"
    private var mDatabaseReference : DatabaseReference?= null
    private var mAuth : FirebaseAuth ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mAuth = FirebaseAuth.getInstance()
        var number : String? = mAuth!!.currentUser!!.phoneNumber
        var visit_count : Int ?= null
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        var kVisitor = am.tk.testapp.Visitors(number, 1)
        if (number != null) {
            mDatabaseReference!!.child(number).setValue(kVisitor)
        }
        if (number != null ) {
            mDatabaseReference!!.addChildEventListener( object : ChildEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    if(p0.getValue(String::class.java)!!.equals(number)){
                        mDatabaseReference!!.child(number).child("visit_count").addChildEventListener(object : ChildEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                                visit_count = p0.getValue(Int::class.java) as Int + 1
                                var kVisitor = am.tk.testapp.Visitors(number, visit_count!!)
                                mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                                if (number != null) {
                                    mDatabaseReference!!.child(number).setValue(kVisitor)
                                }

                            }

                            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                                visit_count = p0.getValue(Int::class.java) as Int + 1
                                var kVisitor = am.tk.testapp.Visitors(number, visit_count!!)
                                mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                                if (number != null) {
                                    mDatabaseReference!!.child(number).setValue(kVisitor)
                                }
                            }

                            override fun onChildRemoved(p0: DataSnapshot) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }
                        })

                    }
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    if(p0.getValue(String::class.java)!!.equals(number)) {
                        mDatabaseReference!!.child(number).child("visit_count")
                            .addChildEventListener(object : ChildEventListener {
                                override fun onCancelled(p0: DatabaseError) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                                    visit_count = p0.getValue(Int::class.java) as Int + 1
                                    var kVisitor = am.tk.testapp.Visitors(number, visit_count!!)
                                    mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                                    if (number != null) {
                                        mDatabaseReference!!.child(number).setValue(kVisitor)
                                    }

                                }

                                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                                    visit_count = p0.getValue(Int::class.java) as Int + 1
                                    var kVisitor = am.tk.testapp.Visitors(number, visit_count!!)
                                    mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                                    if (number != null) {
                                        mDatabaseReference!!.child(number).setValue(kVisitor)
                                    }
                                }

                                override fun onChildRemoved(p0: DataSnapshot) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }
                            })
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }

    }
}
