package com.example.threads.Screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.threads.R
import com.example.threads.ViewModel.AuthViewModel
import com.example.threads.navigation.Routes

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun login(navController: NavHostController){

    val authViewModel : AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()
    val error by authViewModel.error.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(firebaseUser){
        if (firebaseUser != null){
            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
    LaunchedEffect(error){
        if (error != null){
            navController.navigate(Routes.login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
error?.let {
    Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
}

    var email by remember{
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )

    {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = null,
            modifier = Modifier.size(115.dp),
            alignment = Alignment.Center
        )

      Text(text = "Login", style = TextStyle(
          fontWeight = FontWeight.Bold,
          fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
          fontSize = 30.sp
      ))

        Box(modifier = Modifier.height(50.dp))

        OutlinedTextField(value = email, onValueChange = {email = it},
            label = { Text("Email")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ), singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color(0xFF484F55),
                cursorColor = Color(0xFF484F55),
                focusedBorderColor = Color(0xFFDDE6D5),
                unfocusedBorderColor = Color(0xFFDDE6D5)
            )
        )
        Box(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = {password = it},
            label = { Text("password")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ), singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(30.dp))

        ElevatedButton(onClick = {

            if (email.isEmpty() || password.isEmpty()){
             Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }else{


                                 authViewModel.login(email,password,context)}
                                 },
            colors = ButtonDefaults.buttonColors()) {
            Text(text = "Login", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }
        TextButton(onClick = {

        }) {
            Text(text = "Forgotten Password?", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ))
        }

        Spacer(modifier = Modifier.weight(1f))
        ElevatedButton(onClick = { navController.navigate(Routes.register.routes){
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        } },
            colors = ButtonDefaults.buttonColors()) {
            Text(text = "Create new account", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }

    }
}