package com.nqmgaming.notesapptesting.core.domain.repository

import com.nqmgaming.notesapptesting.core.domain.model.NoteItem

interface NoteRepository {
    suspend fun upsertNote(noteItem: NoteItem)

    suspend fun deleteNote(noteItem: NoteItem)

    suspend fun getAllNotes(): List<NoteItem>

    suspend fun searchNotes(query: String): List<NoteItem>

    suspend fun getNoteById(id: Int): NoteItem

}