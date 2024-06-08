package com.nqmgaming.notesapptesting.note_list.use_case

import com.nqmgaming.notesapptesting.core.domain.model.NoteItem
import com.nqmgaming.notesapptesting.core.domain.repository.NoteRepository

class DeleteNote(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        noteItem: NoteItem
    ){
        noteRepository.deleteNote(noteItem)
    }
}