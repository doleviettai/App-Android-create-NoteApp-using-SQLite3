package com.example.noteappsqlite_jetpack.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Int): Note?

    @Query("SELECT * FROM note WHERE title LIKE :title")
    fun getNoteByTitle(title: String): Note?

    @Query("SELECT * FROM note ORDER BY dateAdded")
    fun getNotesOrderedByDateAdded() : Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY title ASC")
    fun getNotesOrderedByTitle() : Flow<List<Note>>

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}