package com.example.threads.Screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.threads.ViewModel.HomeViewModel
import com.example.threads.item_view.ThreadItem
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(navHostController: NavHostController){
    val context = LocalContext.current
    val homeViewModel : HomeViewModel = viewModel()
    val threadsAndUsers by homeViewModel.threadsAndUsers.observeAsState(null)

    LazyColumn   {
       items(threadsAndUsers?: emptyList()){pairs ->
          ThreadItem(
              Thread = pairs.first,
                users = pairs.second,
              navHostController,
              FirebaseAuth.getInstance().currentUser!!.uid
          )
            }
    }
}


//@Preview(showBackground = true   )
//@Composable
//fun PreviewHome(){
//    Home()
//}