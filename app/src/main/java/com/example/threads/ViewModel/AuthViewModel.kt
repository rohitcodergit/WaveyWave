package com.example.threads.ViewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threads.model.UserModel
import com.example.threads.utils.SharedPrefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID


class AuthViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
   private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser : LiveData<FirebaseUser> = _firebaseUser

    private val _error  = MutableLiveData<String>()
    val error : MutableLiveData<String> = _error


    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
            if (it.isSuccessful){
                _firebaseUser.value = auth.currentUser
                getData(auth.currentUser!!.uid,context)
            }else{
                _error.postValue(it.exception!!.message)
            }
        }
    }

    private fun getData(uid: String,context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                val userdata = snapshot.getValue(UserModel::class.java)// use for getting data from firebase
                SharedPrefs.StoreData(userdata!!.name,
                    userdata.email,
                    userdata.bio,userdata.username,userdata!!.imageUrl,context)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun Register(
        email: String,
        password: String,
        name: String,
        username: String,
        Bio: String,
        imageUri: Uri,
        context: Context
    ){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                _firebaseUser.value = auth.currentUser
                saveImage(email,password,name,Bio,username,imageUri,auth.currentUser?.uid,context)

            }else{
                _error.postValue("Something went wrong")

            }
        }
    }

    private fun saveImage(email: String, password: String,
                          name: String,
                          bio: String,
                          username: String,
                          imageUri: Uri,
                          uid: String?,
                          context: Context) {

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
               saveData(email,password,name,bio,username,it.toString(),uid,context)
            }
        }
    }

    private fun saveData(email: String,
                         password: String,
                         name: String,
                         bio: String,
                         username: String,
                         toString: String,
                         uid: String?,
                         context: Context) {

        val userData = UserModel(name,email,password,username,bio,toString,uid!!)

        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {
                SharedPrefs.StoreData(name,email,bio,username,toString,context)
            }.addOnFailureListener(){

            }
        }
    fun logout(){
        auth.signOut()
        _firebaseUser.value = (null)}

    }
