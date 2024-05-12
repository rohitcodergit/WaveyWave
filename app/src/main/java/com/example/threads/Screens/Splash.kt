
package com.example.threads.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.threads.R
import com.example.threads.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun Splash(navController:NavHostController){


        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blue))){
            // Create references for the composables to constrain
            val (image) = createRefs()
              Image(painter = painterResource(id = R.drawable.logo1), contentDescription = null,
                  modifier = Modifier
                      .constrainAs(image) {
                          // top.linkTo(parent.top, margin = 32.dp)
                          top.linkTo(parent.top) // 32.dp
                          bottom.linkTo(parent.bottom) // 32.dp
                          start.linkTo(parent.start) // 32.dp
                          end.linkTo(parent.end) // 32.dp
                      }
                      .size(120.dp)
                  )
        }

    LaunchedEffect( true){
       delay(3000)

        if (FirebaseAuth.getInstance().currentUser != null)
        navController.navigate(Routes.BottomNav.routes)
        else
            navController.navigate(Routes.login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
    }
}