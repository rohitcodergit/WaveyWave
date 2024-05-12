package com.example.threads.model

import androidx.compose.ui.graphics.vector.ImageVector
//used in bottomNavItem in MyBottomBar
data class bottomNavItem(
    val title :String,
    val route :String,
    val icon : ImageVector
)
