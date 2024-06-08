package com.nqmgaming.notesapptesting.note_list.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nqmgaming.notesapptesting.core.data.FakeNoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllNotesTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var getAllNotes: GetAllNotes

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getAllNotes = GetAllNotes(fakeNoteRepository)
    }

    @Test
    fun `get all notes from empty repository`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)
        val notes = getAllNotes.invoke(true)
        assertThat(notes).isEmpty()
    }

    @Test
    fun `get all notes sorted by title`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        val notes = getAllNotes.invoke(true)

        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `get all notes sorted by date`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        val notes = getAllNotes.invoke(false)

        for (i in 0 until notes.size - 1) {
            assertThat(notes[i].dateAdded).isLessThan(notes[i + 1].dateAdded)
        }
    }

}