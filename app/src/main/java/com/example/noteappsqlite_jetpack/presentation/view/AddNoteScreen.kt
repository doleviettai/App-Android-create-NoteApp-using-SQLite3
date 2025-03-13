package com.example.noteappsqlite_jetpack.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check

import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteappsqlite_jetpack.presentation.NoteState
import com.example.noteappsqlite_jetpack.presentation.NotesEvents

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
                        description = state.description.value
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
            // ✅ Thêm tiêu đề đẹp hơn
            Text(
                text = "Thêm dữ liệu ghi nhớ",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // ✅ TextField Title với OutlinedTextField
            OutlinedTextField(
                value = state.title.value,
                onValueChange = { state.title.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                shape = RoundedCornerShape(12.dp), // Bo góc
                label = { Text("Tiêu đề") }, // Tiêu đề cho TextField
                leadingIcon = { Icon(Icons.Default.Star, contentDescription = "Title") }, // Biểu tượng bên trái
                colors = TextFieldDefaults.colors(  // ✅ Sử dụng TextFieldDefaults.colors() thay vì lỗi
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF6200EE),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFF6200EE)
                )
            )

            Spacer(modifier = Modifier.height(16.dp)) // Thêm khoảng cách

            // ✅ TextField Description với OutlinedTextField
            OutlinedTextField(
                value = state.description.value,
                onValueChange = { state.description.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
                shape = RoundedCornerShape(12.dp), // Bo góc
                label = { Text("Mô tả") }, // Tiêu đề cho TextField
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Description") }, // Biểu tượng bên trái
                colors = TextFieldDefaults.colors(  // ✅ Sửa lại phần màu để đúng với Material3
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
