package com.example.douglasproject

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersActivity : AppCompatActivity() {
    lateinit var listusers : ListView
    lateinit var adapter: CustomAdapter
    lateinit var users :ArrayList<User>
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        listusers = findViewById(R.id.mListUsers)
        users = ArrayList()
        adapter = CustomAdapter(this,users)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Please wait...")
        val reference = FirebaseDatabase.getInstance().getReference().child("Users")
        progressDialog.show()
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                users.clear()
                for (snap in snapshot.children){
                    var user = snap.getValue(User::class.java)
                    users.add(user!!)
                }
                adapter.notifyDataSetChanged()
                progressDialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Toast.makeText(applicationContext,"DB locked",Toast.LENGTH_LONG).show()
            }
        })
        listusers.adapter = adapter

    }
}