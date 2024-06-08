package com.nqmgaming.notesapptesting.core.data.repository

import com.nqmgaming.notesapptesting.core.data.local.NoteDb
import com.nqmgaming.notesapptesting.core.data.mapper.toNoteEntityForInsert
import com.nqmgaming.notesapptesting.core.data.mapper.toNoteItem
import com.nqmgaming.notesapptesting.core.domain.model.NoteItem
import com.nqmgaming.notesapptesting.core.domain.repository.NoteRepository

class NoteRepositoryImpl(
    noteDb: NoteDb
) : NoteRepository {
    private val noteDao = noteDb.noteDao

    override suspend fun upsertNote(noteItem: NoteItem) {
        noteDao.upsertNoteEntity(noteItem.toNoteEntityForInsert())
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        noteDao.deleteNoteEntity(noteItem.toNoteEntityForInsert())
    }

    override suspend fun getAllNotes(): List<NoteItem> {
        return noteDao.getAllNoteEntities().map { it.toNoteItem() }
    }

    override suspend fun searchNotes(query: String): List<NoteItem> {
        return noteDao.searchNoteEntities(query).map { it.toNoteItem() }
    }

    override suspend fun getNoteById(id: Int): NoteItem {
        return noteDao.getNoteEntityById(id).toNoteItem()
    }
}