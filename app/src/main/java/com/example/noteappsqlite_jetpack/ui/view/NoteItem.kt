package com.example.noteappsqlite_jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteappsqlite_jetpack.presentation.NoteState
import com.example.noteappsqlite_jetpack.presentation.NotesEvents


@Composable
fun NoteItem(
    state: NoteState,
    index : Int,
    onEvent : (NotesEvents) -> Unit,
    navController: NavController
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.onPrimary)
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
                navController.navigate("UpdateNoteScreen/${state.notes[index].id}")
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