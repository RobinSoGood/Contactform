package com.example.contactform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var edPhone: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView

    private var adapter: UserAdapter? = null
    private var std:UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InitView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{ addUser()}
        btnView.setOnClickListener { getUsers() }


        adapter?.setOnClickItem{
            Toast.makeText(this,it.name, Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            edPhone.setText(it.phone)
            std = it
        }
        adapter?.setOnClickDeleteItem {
            deleteUser(it.id)
        }

    }
    private fun getUsers() {
        val stdList = sqliteHelper.getAllUser()
        Log.e("ppp","${stdList.size}")
        adapter?.addItems(stdList)

    }

    private fun addUser() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val phone = edPhone.text.toString()

        if(name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Пожалуйста заполните все строки", Toast.LENGTH_SHORT).show()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email введен некорректно", Toast.LENGTH_SHORT).show()
        }
        else {
            val std  = UserModel(name = name,email = email,phone = phone)
            val status = sqliteHelper.insertUser(std)
            if (status > -2){
                Toast.makeText(this,"Пользователь добавлен", Toast.LENGTH_SHORT).show()
                clearEditText()
                getUsers()
            }else{
                Toast.makeText(this,"Ошибка", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun deleteUser(id:Int){
        if(id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Удалить пользователя?")
        builder.setCancelable(true)
        builder.setPositiveButton("Да"){dialog,_ ->
            sqliteHelper.deleteUserById(id)
            getUsers()
            dialog.dismiss()
        }
        builder.setNegativeButton("Нет"){dialog,_ ->

            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }


    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edPhone.setText("")
        edName.requestFocus()

    }
    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        recyclerView.adapter = adapter
    }
    private fun InitView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        edPhone = findViewById(R.id.edPhone)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        recyclerView = findViewById(R.id.recyclerView)
    }


}