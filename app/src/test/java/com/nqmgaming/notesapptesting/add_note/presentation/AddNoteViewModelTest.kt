package com.nqmgaming.notesapptesting.add_note.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nqmgaming.notesapptesting.MainCoroutineRule
import com.nqmgaming.notesapptesting.add_note.domain.use_case.SearchImages
import com.nqmgaming.notesapptesting.add_note.domain.use_case.UpsertNote
import com.nqmgaming.notesapptesting.add_note.presentation.util.Resource
import com.nqmgaming.notesapptesting.core.data.FakeImagesRepository
import com.nqmgaming.notesapptesting.core.data.FakeNoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var upsertNote: UpsertNote
    private lateinit var searchImages: SearchImages
    private lateinit var addNoteViewModel: AddNoteViewModel

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        fakeImagesRepository = FakeImagesRepository()
        upsertNote = UpsertNote(fakeNoteRepository)
        searchImages = SearchImages(fakeImagesRepository)
        addNoteViewModel = AddNoteViewModel(upsertNote, searchImages)
    }

    @Test
    fun `upsert note with empty title, return false`() = runTest {
        val inInserted = addNoteViewModel.saveNote(
            title = "",
            description = "content",
            imageUrl = "image"
        )
        assertThat(inInserted).isFalse()
    }

    @Test
    fun `upsert note with empty description, return false`() = runTest {
        val inInserted = addNoteViewModel.saveNote(
            title = "title",
            description = "",
            imageUrl = "image"
        )
        assertThat(inInserted).isFalse()
    }

    @Test
    fun `upsert note with empty imageUrl, return false`() = runTest {
        val inInserted = addNoteViewModel.saveNote(
            title = "title",
            description = "content",
            imageUrl = ""
        )
        assertThat(inInserted).isFalse()
    }

    @Test
    fun `upsert note with valid data, return true`() = runTest {
        val inInserted = addNoteViewModel.saveNote(
            title = "title",
            description = "content",
            imageUrl = "image"
        )
        assertThat(inInserted).isTrue()
    }

    @Test
    fun `search images with empty query, returns empty`() = runTest {
        addNoteViewModel.searchImages("")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertThat(addNoteViewModel.addNoteState.value.imageList.isEmpty()).isTrue()
    }

    @Test
    fun `search images a valid query but with network error, returns empty`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)
        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertThat(addNoteViewModel.addNoteState.value.imageList.isEmpty()).isTrue()
    }

    @Test
    fun `search images with a valid query, returns success`() = runTest {
        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertThat(addNoteViewModel.addNoteState.value.imageList.isEmpty()).isFalse()
    }

    @Test
    fun `search images with a valid query, list is not empty`() = runTest {
        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertThat(addNoteViewModel.addNoteState.value.imageList.isEmpty()).isFalse()
    }
}