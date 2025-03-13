package com.example.noteappsqlite_jetpack.presentation.view


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteappsqlite_jetpack.R
import com.example.noteappsqlite_jetpack.data.Note
import com.example.noteappsqlite_jetpack.presentation.NoteState
import com.example.noteappsqlite_jetpack.presentation.NotesEvents

@SuppressLint("ResourceType")
@Composable
fun NotesScreen(
    note: Note,
    state : NoteState,
    navController: NavController,
    onEvent : (NotesEvents) -> Unit
){
    Scaffold(
        modifier = Modifier.background(Color.White), // Đặt màu nền trắng
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth().background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(35.dp)) // Đẩy TopBar xuống
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "NoteApp",
                        modifier = Modifier.weight(1f),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    IconButton(onClick = { onEvent(NotesEvents.SortNotes) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sort_24),
                            contentDescription = "Sort Note",
                            modifier = Modifier.size(35.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    state.title.value = ""
                    state.description.value = ""
                    navController.navigate("AddNoteScreen")
                          },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Add Note"
                )
            }
        }
    ) {
        paddingValues ->

        LazyColumn (
            contentPadding  = paddingValues ,
            modifier = Modifier.fillMaxSize().background(Color.White).padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(state.notes.size){
                index ->

                NoteItem(
                    state = state,
                    index = index,
                    onEvent = onEvent,
                    navController = navController,
                    note = note
                )
            }

        }

    }
}

@Composable
fun NoteItem(
    state: NoteState,
    index : Int,
    onEvent : (NotesEvents) -> Unit,
    navController: NavController,
    note: Note
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.onPrimary)
//            .border(5.dp, MaterialTheme.colorScheme.onPrimaryContainer)
            .shadow(
                elevation = 1.dp,
                shape = MaterialTheme.shapes.medium,
                clip = true,
                ambientColor = MaterialTheme.colorScheme.onPrimaryContainer,
                spotColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)

        ) {
            Text(
                text = state.notes[index].title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            Text(
                text = state.notes[index].description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        IconButton(
            onClick = {
                navController.navigate("UpdateNoteScreen/${note.id}")
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edit Note",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }


        IconButton(
            onClick = {
                onEvent(NotesEvents.DeleteNote(state.notes[index]))
            }
        ) {
           Icon(
               imageVector = Icons.Default.Delete,
               contentDescription = "Delete Note",
               modifier = Modifier.size(30.dp),
               tint = MaterialTheme.colorScheme.onPrimaryContainer
           )
        }
    }
}