package com.example.douglasproject

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtIdnumber: EditText
    lateinit var edtEmail: EditText
    lateinit var buttonSave: Button
    lateinit var buttonView: Button

    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.mEdtName)
        edtIdnumber = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        buttonSave = findViewById(R.id.mBtnLogin)
        buttonView = findViewById(R.id.mBtnView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please Wait...")
        buttonSave.setOnClickListener {
            //Start by receiving data from the user
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val idNumber = edtIdnumber.text.toString().trim()
            val time = System.currentTimeMillis().toString()
            if (name.isEmpty() || email.isEmpty() || idNumber.isEmpty()) {
                Toast.makeText(applicationContext, "Please Fill all inputs", Toast.LENGTH_SHORT).show()
            }else{
                //Prepare the data on a user object
                val userData = User(name,email,idNumber,time)
                val reference = FirebaseDatabase.getInstance().getReference().child("Users/$time")
                progressDialog.show()
                reference.setValue(userData).addOnCompleteListener {
                    task->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(applicationContext, "Saving Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        buttonView.setOnClickListener {
            startActivity(Intent(applicationContext,UsersActivity::class.java))
        }

    }
}