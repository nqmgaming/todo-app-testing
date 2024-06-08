package com.nqmgaming.notesapptesting.note_list.use_case.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nqmgaming.notesapptesting.MainCoroutineRule
import com.nqmgaming.notesapptesting.core.data.FakeNoteRepository
import com.nqmgaming.notesapptesting.note_list.presentation.NoteListViewModel
import com.nqmgaming.notesapptesting.note_list.use_case.DeleteNote
import com.nqmgaming.notesapptesting.note_list.use_case.GetAllNotes
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NoteListViewModelTest {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNote: DeleteNote
    private lateinit var getAllNotes: GetAllNotes

    private lateinit var noteListViewModel: NoteListViewModel

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeNoteRepository)
        getAllNotes = GetAllNotes(fakeNoteRepository)
        noteListViewModel = NoteListViewModel(getAllNotes, deleteNote)
    }

    @Test
    fun `get notes from empty list, note list is empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)

        noteListViewModel.loadNotes()

        assertThat(
            noteListViewModel.noteListState.value.isEmpty()
        ).isTrue()
    }

    @Test
    fun `get notes from filled list, note list is not empty`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)

        noteListViewModel.loadNotes()

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // to wait for the coroutine to finish

        assertThat(
            noteListViewModel.noteListState.value.isNotEmpty()
        ).isTrue()
    }

    @Test
    fun `delete note from list, note is deleted`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        noteListViewModel.loadNotes()

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // to wait for the coroutine to finish
        val note = fakeNoteRepository.getAllNotes().first()

        noteListViewModel.deleteNote(note)
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // to wait for the coroutine to finish

        assertThat(
            noteListViewModel.noteListState.value.contains(note)
        ).isFalse()
    }

    @Test
    fun `sort notes by date, notes are sorted by date`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        noteListViewModel.loadNotes()

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // to wait for the coroutine to finish

        val notes = noteListViewModel.noteListState.value

        for (i in 0 until notes.size - 1) {
            assertThat(
                notes[i].dateAdded <= notes[i + 1].dateAdded
            ).isTrue()
        }
    }

    @Test
    fun `sort notes by title, notes are sorted by title`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(true)
        noteListViewModel.loadNotes()

        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // to wait for the coroutine to finish

        noteListViewModel.changeOrder()
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle() // to wait for the coroutine to finish

        val notes = noteListViewModel.noteListState.value

        for (i in 0 until notes.size - 1) {
            assertThat(
                notes[i].title <= notes[i + 1].title
            ).isTrue()
        }
    }
}