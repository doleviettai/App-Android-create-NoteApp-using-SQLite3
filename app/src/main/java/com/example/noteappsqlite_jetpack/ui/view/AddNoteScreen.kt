package com.example.noteappsqlite_jetpack.ui.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter

import com.example.noteappsqlite_jetpack.R
import com.example.noteappsqlite_jetpack.presentation.NoteState
import com.example.noteappsqlite_jetpack.presentation.NotesEvents
import com.example.noteappsqlite_jetpack.ui.theme.NoteAppSQLite_JetpackTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvents) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(
                    NotesEvents.SaveNote(
                        title = state.title.value,
                        description = state.description.value,
                    )
                )
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save Note")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ✅ Tiêu đề màn hình
            Text(
                text = "Thêm dữ liệu ghi nhớ",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ TextField Title
            OutlinedTextField(
                value = state.title.value,
                onValueChange = { state.title.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                shape = RoundedCornerShape(12.dp),
                label = { Text("Tiêu đề") },
                leadingIcon = { Icon(Icons.Default.Star, contentDescription = "Title") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF6200EE),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFF6200EE)
                )
            )

            Spacer(modifier = Modifier.height(16.dp)) // Thêm khoảng cách

            // ✅ TextField Description
            OutlinedTextField(
                value = state.description.value,
                onValueChange = { state.description.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
                shape = RoundedCornerShape(12.dp),
                label = { Text("Mô tả") },
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Description") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF6200EE),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFF6200EE)
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ShowAddNoteScreen() {
    NoteAppSQLite_JetpackTheme {
        AddNoteScreen(
            state = NoteState(),
            navController = rememberNavController(),
            onEvent = {}
        )
    }
}
