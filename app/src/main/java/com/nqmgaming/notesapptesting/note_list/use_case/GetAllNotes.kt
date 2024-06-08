package com.nqmgaming.notesapptesting.note_list.use_case

import com.nqmgaming.notesapptesting.core.domain.model.NoteItem
import com.nqmgaming.notesapptesting.core.domain.repository.NoteRepository

class GetAllNotes(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        isOrderByTitle: Boolean = false
    ): List<NoteItem> {
        return if (isOrderByTitle) {
            noteRepository.getAllNotes().sortedBy { it.title }
        } else {
            noteRepository.getAllNotes().sortedBy { it.dateAdded }
        }
    }
}