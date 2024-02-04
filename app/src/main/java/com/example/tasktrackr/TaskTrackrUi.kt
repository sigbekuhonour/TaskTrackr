package com.example.tasktrackr

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

////////////
@Composable
fun WelcomeUiPage(navigation: NavController) {
    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.background1),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                contentDescription = "background_welcome"
            )
            Column {
                Spacer(modifier = Modifier.padding(vertical = 340.dp))
                Button(modifier = Modifier.padding(start = 90.dp),
                    onClick = { navigation.navigate("TodoList") },
                    border = BorderStroke(
                        width = 2.dp, brush = Brush.horizontalGradient(
                            listOf(Color.Black, Color.LightGray)
                        )
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = transparentColor,
                        contentColor = blackColor
                    )
                ) {
                    Text(
                        text = "CLICK TO ADD NOTES", fontSize = 15.sp, fontFamily = taskTrackrFont
                    )
                }

            }

        }
    }
}
    //////////////////
    @RequiresApi(Build.VERSION_CODES.M)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun EditNote(navigation: NavController, viewModel: MyViewModel) {
        //define viewModel
        //val viewModel_List = remember { viewModel.listOfTask}
        //var viewModel_ListSize = viewModel_List.size
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    text = "Your Notes",
                    fontSize = 28.sp, modifier = Modifier.padding(horizontal = 30.dp), fontFamily = taskTrackrFont
                )
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = blackColor,
                    navigationIconContentColor = whiteColor, titleContentColor = whiteColor
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navigation.navigate("LandingPage") }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "BackButton"
                        )
                    }
                }, actions = {
                    IconButton(
                        onClick = {viewModel.createdTask(title = "Task", task = "New_TASK") },
                        colors = IconButtonDefaults.iconButtonColors(whiteColor)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_task),
                            contentDescription = "add"
                        )
                    }
                    IconButton(
                        onClick = {viewModel.delete()},
                        colors = IconButtonDefaults.iconButtonColors(whiteColor)) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete_task),
                            contentDescription = "add"
                        )
                    }
                }
            )
        }, containerColor = blackColor) { paddingValues ->

            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(viewModel.listOfTask.size) { index ->
                    Surface(
                        selected = false,
                        onClick = { navigation.navigate("TextDisplay") },
                        //{fix up the color later}color = //surfaceColor,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(150.dp)
                            .width(385.dp)
                            .padding(horizontal = 15.dp, vertical = 10.dp),
                        contentColor = blackColor
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            //"$index"
                            Text(
                                text ="$index"+".  "+viewModel.listOfTask[index].title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp, maxLines = 2, fontFamily = taskTrackrFont
                            )
                            Text(text = viewModel.listOfTask[index].task, maxLines = 2, fontFamily = taskTrackrFont)
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun TaskDisplay(
        viewModel: MyViewModel,
        navigation: NavController
    ) {
        var titleStr by remember { mutableStateOf(viewModel.listOfTask[0].title) }
        var taskStr by remember { mutableStateOf(viewModel.listOfTask[0].task) }
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
            Surface(
                color = whiteColor,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        IconButton(onClick = {
                            if (taskStr.isNotEmpty() && titleStr.isNotEmpty()) {
                                viewModel.updateTask(titleStr,taskStr)
                            }
                            navigation.navigate("TodoList")
                        }, modifier = Modifier.padding(horizontal = 10.dp)) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "BackButton"
                            )
                        }
                        OutlinedTextField(
                            value = titleStr, onValueChange = { titleStr = it },
                            textStyle = TextStyle(fontFamily = taskTrackrFont, fontSize = 35.sp),
                            colors = TextFieldDefaults
                                .colors(
                                    unfocusedContainerColor = transparentColor,
                                    focusedContainerColor = transparentColor,
                                    focusedIndicatorColor = transparentColor,
                                    unfocusedIndicatorColor = transparentColor
                                )
                        )
                    }
                    TextField(value = taskStr, onValueChange = { taskStr = it },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        textStyle = TextStyle(fontFamily = taskTrackrFont, fontSize = 15.sp),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = transparentColor,
                            focusedContainerColor = transparentColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = true,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                //viewModel.listOfTask.indexOf(Task(task,title))
                                viewModel.updateTask(titleStr,taskStr)
                                navigation.navigate("TodoList")
                            }
                        )
                    )

                }
            }
        }
    }
