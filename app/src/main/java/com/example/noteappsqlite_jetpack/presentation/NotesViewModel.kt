package com.example.noteappsqlite_jetpack.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappsqlite_jetpack.data.Note
import com.example.noteappsqlite_jetpack.data.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val dao : NoteDao
) : ViewModel(){

    private val isSortedByDateAdded = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var notes = isSortedByDateAdded.flatMapLatest {
        sort ->
        if (sort){
            dao.getNotesOrderedByDateAdded()
        }else{
            dao.getNotesOrderedByTitle()
        }
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed() , emptyList())

    val _state = MutableStateFlow(NoteState())
    val state = combine(_state,isSortedByDateAdded , notes){
        state , isSortedByDateAdded , notes ->
            state.copy(
                notes = notes
            )
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000) , NoteState())

    fun onEvent(event: NotesEvents) {
        when (event) {
            is NotesEvents.DeleteNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.deleteNote(event.note)
                }
            }

            is NotesEvents.SaveNote -> {
                val note = Note(
                    title = event.title,
                    description = event.description,
                    dateAdded = System.currentTimeMillis()
                )


                viewModelScope.launch(Dispatchers.IO) {
                    dao.insertNote(note)
                }
            }

            NotesEvents.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }

            is NotesEvents.UpdateNote -> {
                _state.update {
                    it.copy(
                        title = mutableStateOf(event.title),
                        description = mutableStateOf(event.description)
                    )
                }

                val note = Note(
                    id = event.id,
                    title = event.title,
                    description = event.description,
                    dateAdded = System.currentTimeMillis()
                )

                viewModelScope.launch(Dispatchers.IO) {
                    dao.updateNote(note)
                }
            }
        }

    }
}