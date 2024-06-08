package com.nqmgaming.notesapptesting.add_note.domain.use_case

import com.nqmgaming.notesapptesting.core.domain.model.NoteItem
import com.nqmgaming.notesapptesting.core.domain.repository.NoteRepository

class UpsertNote(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        imageUrl: String,
    ): Boolean {
        if (title.isEmpty() || description.isEmpty() || imageUrl.isEmpty()) {
            return false
        }
        val noteItem = NoteItem(
            title = title,
            description = description,
            imageUrl = imageUrl,
            dateAdded = System.currentTimeMillis()
        )
        noteRepository.upsertNote(noteItem)
        return true
    }
}