package com.example.threads.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.threads.model.ThreadModel
import com.example.threads.model.UserModel
@Composable
fun ThreadItem(
    Thread: ThreadModel,
    users: UserModel,
    navHostController: NavHostController,
    userId: String
) {

    Column{
        ConstraintLayout(
            modifier = Modifier
//                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (userImage,userName,date,time,title,image)= createRefs()

            // Image
            Image(painter = rememberAsyncImagePainter(model =users.imageUrl ),
                contentDescription ="close",

                modifier = Modifier
                    .constrainAs(userImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .size(34.dp)
                    .clip(shape = CircleShape)

            )
            Text(
                text = users.username,
                style = TextStyle(
                    fontSize = 20.sp,
                ),
                modifier = Modifier.constrainAs(userName) {
                    top.linkTo(userImage.top)
                    start.linkTo(userImage.end, margin = 8.dp)
                    bottom.linkTo(userImage.bottom)

                }
            )
            Text(
                text = Thread.Thread,
                style = TextStyle(
                    fontSize = 18.sp,
                ),
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(userName.bottom , margin = 8.dp)
                        start.linkTo(userName.start)
                }
            )

            if (Thread.image != ""){
                Card (modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    Image(painter = rememberAsyncImagePainter(model = Thread.image),
                        contentDescription = "close",

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) ,
                        contentScale = ContentScale.Crop

                    )
                }

            }

        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }


}

