package com.nqmgaming.notesapptesting.add_note.domain.use_case

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nqmgaming.notesapptesting.core.data.FakeNoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertNoteTest {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var upsertNote: UpsertNote

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        upsertNote = UpsertNote(fakeNoteRepository)
        fakeNoteRepository.shouldHaveFilledList(false)
    }

    @Test
    fun `upsert note with empty title, return false`() = runTest {
        val inInserted = upsertNote.invoke(
            title = "",
            description = "content",
            imageUrl = "image"
        )

        assertThat(inInserted).isFalse()

    }

    @Test
    fun `upsert note with empty description, return false`() = runTest {
        val inInserted = upsertNote.invoke(
            title = "title",
            description = "",
            imageUrl = "image"
        )

        assertThat(inInserted).isFalse()

    }

    @Test
    fun `upsert note with empty imageUrl, return false`() = runTest {
        val inInserted = upsertNote.invoke(
            title = "title",
            description = "content",
            imageUrl = ""
        )

        assertThat(inInserted).isFalse()

    }

    @Test
    fun `upsert note with valid data, return true`() = runTest {
        val inInserted = upsertNote.invoke(
            title = "title",
            description = "content",
            imageUrl = "image"
        )

        assertThat(inInserted).isTrue()

    }
}