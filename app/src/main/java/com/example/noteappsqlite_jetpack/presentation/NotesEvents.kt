package com.example.noteappsqlite_jetpack.presentation

import com.example.noteappsqlite_jetpack.data.Note

sealed interface NotesEvents {
    object SortNotes : NotesEvents

    data class DeleteNote(val note: Note) : NotesEvents

    data class SaveNote(
        val title: String,
        val description: String,
    ) : NotesEvents

    data class UpdateNote(
        val id: Int,
        val title: String,
        val description: String,
    ) : NotesEvents
}
