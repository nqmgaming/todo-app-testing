package com.nqmgaming.notesapptesting.core.data.repository

import com.nqmgaming.notesapptesting.core.domain.model.NoteItem
import com.nqmgaming.notesapptesting.core.domain.repository.NoteRepository

class FakeAndroidNoteRepository(
) : NoteRepository {

    private var noteItems = mutableListOf<NoteItem>()

    fun shouldHaveFilledList(shouldHaveFilledList: Boolean) {
        noteItems =
            if (shouldHaveFilledList) {
                mutableListOf(
                    NoteItem("d title 1", "desc 1", "url1", 1),
                    NoteItem("c title 2", "desc 2", "url2", 2),
                    NoteItem("b title 3", "desc 3", "url3", 3),
                    NoteItem("a title 4", "desc 4", "url4", 4)
                )
            } else {
                mutableListOf()
            }
    }

    override suspend fun upsertNote(noteItem: NoteItem) {
        noteItems.add(noteItem)
    }

    override suspend fun deleteNote(noteItem: NoteItem) {
        noteItems.remove(noteItem)
    }

    override suspend fun getAllNotes(): List<NoteItem> {
        return noteItems
    }

    override suspend fun searchNotes(query: String): List<NoteItem> {
        return noteItems.filter {
            it.title.contains(query, ignoreCase = true) || it.description.contains(
                query,
                ignoreCase = true
            )
        }
    }

    override suspend fun getNoteById(id: Int): NoteItem {
        return noteItems.first { it.id == id }
    }
}