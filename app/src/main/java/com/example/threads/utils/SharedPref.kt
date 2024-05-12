package com.example.threads.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPrefs {

    fun StoreData(name:String,
                  email:String,
                  bio:String,
                  userName:String,
                  imageUrl:String,
                  context: Context){
        val sharePrefereces = context.getSharedPreferences("user",MODE_PRIVATE)
        val editor = sharePrefereces.edit()
        editor.putString("name",name)
        editor.putString("email",email)
        editor.putString("bio",bio)
        editor.putString("userName",userName)
        editor.putString("imageUrl",imageUrl)
        editor.apply()
    }
    fun getUserName(context: Context):String?{
        val sharePrefereces = context.getSharedPreferences("user",MODE_PRIVATE)
        return sharePrefereces.getString("userName","")
    }
    fun getEmail(context: Context):String?{
        val sharePrefereces = context.getSharedPreferences("user",MODE_PRIVATE)
        return sharePrefereces.getString("email","")
    }
    fun getName(context: Context):String?{
        val sharePrefereces = context.getSharedPreferences("user",MODE_PRIVATE)
        return sharePrefereces.getString("name","")
    }
    fun getBio(context: Context):String?{
        val sharePrefereces = context.getSharedPreferences("user",MODE_PRIVATE)
        return sharePrefereces.getString("bio","")
    }
    fun getImageUrl(context: Context):String?{
        val sharePrefereces = context.getSharedPreferences("user",MODE_PRIVATE)
        return sharePrefereces.getString("imageUrl","")
    }

}