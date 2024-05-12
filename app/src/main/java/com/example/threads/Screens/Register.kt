package com.example.threads.Screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threads.R
import com.example.threads.ViewModel.AuthViewModel
import com.example.threads.navigation.Routes

@Composable
fun Register(navController: NavHostController){



    var email by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var bio by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val authViewModel: AuthViewModel = viewModel()
    val  firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val permissionToRequest = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent() ){
        uri : Uri? ->
        imageUri = uri
    }


    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()
    ){ isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted
        } else {
            // Permission has been denied
        }
    }


    LaunchedEffect(firebaseUser){
        if (firebaseUser != null){
            navController.navigate(Routes.BottomNav.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.blue))
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )

    {
        Text(text = "Register", style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        )
        Box(modifier = Modifier.height(50.dp))
        Image(
            painter =   if(imageUri == null) painterResource(id = R.drawable.user) else rememberAsyncImagePainter(
                model = imageUri
            ),
            contentDescription = null,
            modifier = Modifier
                .size(115.dp)
                .clip(CircleShape)
                .clickable {

                    val isGranted =
                        ContextCompat.checkSelfPermission(context, permissionToRequest) ==
                                PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        launcher.launch("image/*")

                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }
                },
            contentScale = ContentScale.Crop    ,
            alignment = Alignment.Center
        )



        Box(modifier = Modifier.height(50.dp))

        OutlinedTextField(value = name, onValueChange = {name = it},
            label = { Text("Name")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(value = username, onValueChange = {username = it},
            label = { Text("Username")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(value = email, onValueChange = {email = it},
            label = { Text("Email")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(value = bio, onValueChange = {bio = it},
            label = { Text("Bio")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(value = password, onValueChange = {password = it},
            label = { Text("password")
            }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ), singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Box(modifier = Modifier.height(30.dp)){

        }

        ElevatedButton(onClick = {
                 if (name.isEmpty()||username.isEmpty()||email.isEmpty()||bio.isEmpty()||password.isEmpty()||imageUri == null){
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                     }else{
                        authViewModel.Register(email, password, name, username, bio, imageUri!!, context)

                     }

        },
            colors = ButtonDefaults.buttonColors()) {
            Text(text = "Register", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White
            ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }


        Spacer(modifier = Modifier.weight(1f))
        ElevatedButton(onClick = {
            navController.navigate(Routes.login.routes){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
                                 },
            colors = ButtonDefaults.buttonColors()) {
            Text(text = "Login if have one", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)
        }

    }
}


