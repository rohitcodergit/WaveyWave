package com.example.threads.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threads.model.ThreadModel
import com.example.threads.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeViewModel : ViewModel() {
 private val db = FirebaseDatabase.getInstance()
    val Thread = db.getReference("Threads")

    private var _threadsAndUsers  = MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
    val     threadsAndUsers: LiveData<List<Pair<ThreadModel, UserModel>>> = _threadsAndUsers
        init {
            fetchThreadsAndUser{
                _threadsAndUsers.value = it
            }
        }


        private fun fetchThreadsAndUser(onResult: (List<Pair<ThreadModel,UserModel>>)->Unit){
        Thread.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<Pair<ThreadModel,UserModel>>()
                for (threadSnapshot in snapshot.children){
                    val thread = threadSnapshot.getValue(ThreadModel::class.java)
                    thread.let {
                        fetchUserFromThread(it!!){
                            user ->
                            result.add(0,it to user)
                            if(result.size == snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    fun fetchUserFromThread(thread: ThreadModel, onResult: (UserModel)-> Unit){
        db.getReference("users").child(thread.userId) // get the user id from the thread
            .addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {

                    val user = snapshot.getValue(UserModel::class.java)
                   user?.let(onResult)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }
    }


