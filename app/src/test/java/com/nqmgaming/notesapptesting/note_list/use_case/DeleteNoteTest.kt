package com.nqmgaming.notesapptesting.note_list.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nqmgaming.notesapptesting.core.data.FakeNoteRepository
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteNoteTest {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNote: DeleteNote

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)
        fakeNoteRepository.shouldHaveFilledList(true)
    }

    @Test
    fun `delete note from list, note deleted`() = runTest{
        val note = fakeNoteRepository.getAllNotes().first()
        deleteNote.invoke(note)
        assertThat(fakeNoteRepository.getAllNotes()).doesNotContain(note)
    }
}