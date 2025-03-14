package com.example.noteappsqlite_jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.noteappsqlite_jetpack.data.Note
import com.example.noteappsqlite_jetpack.data.NotesDatabase
import com.example.noteappsqlite_jetpack.presentation.NotesViewModel
import com.example.noteappsqlite_jetpack.ui.view.AddNoteScreen
import com.example.noteappsqlite_jetpack.ui.view.NotesScreen
import com.example.noteappsqlite_jetpack.ui.view.UpdateNoteScreen
import com.example.noteappsqlite_jetpack.ui.theme.NoteAppSQLite_JetpackTheme

class MainActivity : ComponentActivity() {

    private val database by lazy{
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NotesViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    return NotesViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            NoteAppSQLite_JetpackTheme {

                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()


                NavHost(
                    navController = navController , startDestination = "NotesScreen"
                ){
                    composable("NotesScreen") {
                        NotesScreen(
                            state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
                    }


                    composable("AddNoteScreen") {
                        AddNoteScreen(
                            state = state,
                            navController = navController ,
                            onEvent = viewModel::onEvent
                        )
                    }

                    composable("UpdateNoteScreen/{id}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
                        val note = state.notes.find {
                            it.id == noteId
                        }

                        if (note != null){
                            UpdateNoteScreen(
                                note = note,
                                navController = navController ,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }

            }
        }
    }
}

