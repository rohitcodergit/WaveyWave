package com.example.threads.ViewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threads.model.ThreadModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID


class AddThreadViewModel : ViewModel() {
   private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("Threads")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("Threads/${UUID.randomUUID()}.jpg")

    private val _isPosted  = MutableLiveData<Boolean>()
    val isPosted: MutableLiveData<Boolean> = _isPosted



     fun saveImage(
                          Thread: String,
                          userId: String,
                          imageUri: Uri) {

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
               saveData(Thread,userId,it.toString())
            }
        }
    }

 fun saveData(
        Thread: String,
        userId: String,
        imageUri: String
    ) {


     val threadData = ThreadModel(Thread,imageUri,userId,System.currentTimeMillis().toString())

        userRef.child(userRef.push().key!!).setValue(threadData)
            .addOnSuccessListener {
               _isPosted.postValue(true)
            }.addOnFailureListener(){
                _isPosted.postValue(false)
            }
        }

    }
