package com.example.contactform

import java.util.*

data class UserModel (
    var id:Int = getAutoId(),
    var name: String = "",
    var email: String = "",
    var phone: String = ""

){
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }

}