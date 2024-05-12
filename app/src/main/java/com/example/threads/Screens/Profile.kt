package com.example.threads.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.threads.ViewModel.AuthViewModel
import com.example.threads.navigation.Routes

@Composable
fun Profile(navHostController: NavHostController)
{
    val AuthViewModel: AuthViewModel = viewModel()
    val firebaseUser by AuthViewModel.firebaseUser.observeAsState(null)

    LaunchedEffect(firebaseUser ){
        if(firebaseUser == null){
           navHostController.navigate(Routes.login.routes){
               popUpTo(navHostController.graph.findStartDestination().id)
                   launchSingleTop = true

           }
        }
    }
    
    Button(onClick = {AuthViewModel.logout()}, modifier = Modifier.padding(16.dp)) {
        Text(text = "Logout")
    }
}