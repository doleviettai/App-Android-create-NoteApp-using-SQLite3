package com.example.noteappsqlite_jetpack.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteappsqlite_jetpack.data.Note
import com.example.noteappsqlite_jetpack.presentation.NoteState
import com.example.noteappsqlite_jetpack.presentation.NotesEvents


@Composable
fun UpdateNoteScreen(
    note: Note,  // Truyền dữ liệu từ danh sách
    navController: NavController,
    onEvent: (NotesEvents) -> Unit
) {
    var titleState = remember { mutableStateOf(note.title) }  // Giữ trạng thái cũ
    var descriptionState = remember { mutableStateOf(note.description) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(NotesEvents.UpdateNote(
                    id = note.id, // Truyền id vào sự kiện
                    title = titleState.value,
                    description = descriptionState.value
                ))
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save Note")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize().background(Color.White)
        ) {
            // ✅ Thêm tiêu đề căn giữa
            Text(
                text = "Sửa dữ liệu ghi nhớ",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = titleState.value,
                onValueChange = { titleState.value = it },
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                shape = RoundedCornerShape(12.dp), // Bo góc
//                placeholder = { Text(text = "Title") },
                leadingIcon = { Icon(Icons.Default.Star, contentDescription = "Title") }, // Biểu tượng bên trái
                colors = TextFieldDefaults.colors(  // ✅ Sử dụng TextFieldDefaults.colors() thay vì lỗi
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF6200EE),
                    unfocusedIndicatorColor = Color.Gray,
                    cursorColor = Color(0xFF6200EE)
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = descriptionState.value,
                onValueChange = { descriptionState.value = it },
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                ),
                shape = RoundedCornerShape(12.dp), // Bo góc
//                placeholder = { Text(text = "Description") },
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
