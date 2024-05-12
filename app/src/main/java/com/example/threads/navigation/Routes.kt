package com.example.threads.navigation

sealed class Routes(val routes: String) {
    object Notification : Routes("Notification")
    object Profile : Routes("Profile")
    object Addthreads : Routes("Addthreads")
    object Splash : Routes("Splash")
    object Home : Routes("home")
    object search: Routes("Search")
    object BottomNav : Routes("Bottom_nav")
    object login : Routes("login")
    object register : Routes("Register")




}