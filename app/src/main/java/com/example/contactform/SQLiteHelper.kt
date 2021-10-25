package com.example.contactform

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "user.db"
        private const val TBL_USER = "tbl_user"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTblUser = ("CREATE TABLE " + TBL_USER + "(" + ID + " INTEGER PRIMARY KEY," + NAME +
                " TEXT," + EMAIL + " TEXT,"  + PHONE + " TEXT" + ")")
        db?.execSQL(createTblUser)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USER")
        onCreate(db)
    }

    fun insertUser(std:UserModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME,std.name )
        contentValues.put(EMAIL,std.email )
        contentValues.put(PHONE,std.phone )

        val success = db.insert(TBL_USER, null, contentValues)
        db.close()
        return success
    }
    @SuppressLint("Range")
    fun getAllUser(): ArrayList<UserModel>{
        val stdList: ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_USER"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch(e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var name:String
        var email:String
        var phone:String

        if(cursor.moveToFirst()){
            do{
                id= cursor.getInt(cursor.getColumnIndex("id"))
                name= cursor.getString(cursor.getColumnIndex("name"))
                email= cursor.getString(cursor.getColumnIndex("email"))
                phone= cursor.getString(cursor.getColumnIndex("phone"))
                val std = UserModel(id = id,name = name,email = email,phone = phone)
                stdList.add(std)
            } while(cursor.moveToNext())
        }
        return stdList
    }

    fun deleteUserById(id:Int): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID,id)

        val success = db.delete(TBL_USER,"id=$id",null)
        db.close()
        return success
    }
}