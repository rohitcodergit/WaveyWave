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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threads.R
import com.example.threads.ViewModel.AddThreadViewModel
import com.example.threads.ViewModel.AuthViewModel
import com.example.threads.navigation.Routes
import com.example.threads.utils.SharedPrefs
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Addthreads(navHostController: NavHostController) {
    val context = LocalContext.current

    var thread by remember {
        mutableStateOf("")
    }
    val threadViewModel: AddThreadViewModel = viewModel()
    val isPosted by threadViewModel.isPosted.observeAsState(false)
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
    LaunchedEffect( isPosted){
        if (isPosted!!){
            thread = ""
            imageUri = null
            Toast.makeText(context, "Thread Added", Toast.LENGTH_SHORT).show()

            navHostController.navigate(Routes.Home.routes){
                popUpTo(Routes.Addthreads.routes){
                    inclusive = true
                }
            }
        }
    }





    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Create references for the composables to constrain

        val (crossPic, text, logo, userName, editText, attachMedia,
            repyText, Button, imageBox) = createRefs()
            
        Image(painter = painterResource(id =R.drawable.cross ),
            contentDescription ="close",
            modifier = Modifier
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable {
                    navHostController.navigate(Routes.Home.routes){
                        popUpTo(Routes.Addthreads.routes){
                            inclusive = true
                        }
                    }
                }
            )
        Text(text = "Add Wave", style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontSize = 30.sp
        ), modifier = Modifier.constrainAs(text) {
         top.linkTo(parent.top)
            start.linkTo(crossPic.end, margin = 16.dp)
            bottom.linkTo(crossPic.bottom)
        }
        )
        Image(painter = rememberAsyncImagePainter(model = SharedPrefs.getImageUrl(context)),
            contentDescription ="close",

            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
                .clickable {
                    //onBackPressed()
                }
                .size(34.dp)
                .clip(shape = CircleShape)

        )
        Text(
            text = SharedPrefs.getUserName(context) ?: "Default Name",
            style = TextStyle(
                fontSize = 16.sp,
            ),
            modifier = Modifier.constrainAs(userName) {
                top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 8.dp)
                bottom.linkTo(logo.bottom)

            }
        )

            BasicTextFieldWithHint(hint = "Start a Wave ..." , value = thread,
                onValueChange = {thread = it} , modifier = Modifier
                    .constrainAs(editText) {
                        top.linkTo(userName.bottom, margin = 16.dp)
                        start.linkTo(userName.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
            if(imageUri == null){
                Image(painter = painterResource(id =R.drawable.baseline_attach_file_24 ),
                    contentDescription ="Attach Media",
                    modifier = Modifier
                        .constrainAs(attachMedia) {
                            top.linkTo(logo.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(editText.start)
                        }
                        .clickable { // this is the code to attach media
                            val isGranted =
                                ContextCompat.checkSelfPermission(context, permissionToRequest) ==
                                        PackageManager.PERMISSION_GRANTED

                            if (isGranted) {
                                launcher.launch("image/*")

                            } else {
                                permissionLauncher.launch(permissionToRequest)
                            }
                        }
                )
            }else{
                Box(modifier = Modifier
                    .background(Color.Gray)
                    .constrainAs(imageBox) {
                        top.linkTo(editText.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(250.dp)){
                    Image(painter = rememberAsyncImagePainter(model =imageUri),
                        contentDescription ="close",

                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop

                    )
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Remove Image",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable { imageUri = null })
                }
            }


        Text(
            text = "Anyone can reply",
            style = TextStyle(
                fontSize = 20.sp,
            ),
            modifier = Modifier.constrainAs(repyText) {
                start.linkTo(parent.start, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 12.dp)


            }
        )
        
        TextButton(onClick = {
                             if (imageUri == null) {
                                 threadViewModel.saveData(
                                     thread,
                                     FirebaseAuth.getInstance().currentUser!!.uid,
                                     ""
                                 )
                             }else{
                                 threadViewModel.saveImage(
                                     thread,
                                     FirebaseAuth.getInstance().currentUser!!.uid,
                                     imageUri!!
                                 )
                             }
                             },
            modifier = Modifier.constrainAs(Button) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom )
            }) {
            Text(
                text = "Post",
                style = TextStyle(
                    fontSize = 20.sp,
                )
            )
        }


        }

    }


@Composable
fun BasicTextFieldWithHint(hint: String,
                           value: String,
                           onValueChange: (String) -> Unit,
                           modifier:Modifier) {

                Box(modifier = modifier){
                    if (value.isEmpty()){
                        Text(text = hint, color = Color.Gray)
                    }
                    BasicTextField(value = value, onValueChange = onValueChange,
                        textStyle = TextStyle.Default.copy(color = Color.Black)
                        )
                }
}

//@Preview(showBackground = true)
//@Composable
//fun AddPostView() {
//    Addthreads()
//}