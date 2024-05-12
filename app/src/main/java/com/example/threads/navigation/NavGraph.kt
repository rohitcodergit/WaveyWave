package com.example.threads.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.threads.Screens.Addthreads
import com.example.threads.Screens.BottomNav
import com.example.threads.Screens.Home
import com.example.threads.Screens.Notification
import com.example.threads.Screens.Profile
import com.example.threads.Screens.Register
import com.example.threads.Screens.Splash
import com.example.threads.Screens.login

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navgraph(navController: NavHostController){

NavHost(navController = navController, startDestination = Routes.Splash.routes ){

    composable(Routes.Splash.routes){
        Splash(navController)
    }
    composable(Routes.Profile.routes){
        Profile(navController)
    }
    composable(Routes.Notification.routes){
        Notification()
    }
    composable(Routes.Addthreads.routes){
        Addthreads(navController)
    }
    composable(Routes.search.routes){
        Routes.search
    }
    composable(Routes.Home.routes){
        Home(navController)
    }
    composable(Routes.BottomNav.routes){
        BottomNav(navController = navController)
    }
    composable(Routes.login.routes){
        login(navController)
    }
    composable(Routes.register.routes){
        Register(navController)
    }

}



}