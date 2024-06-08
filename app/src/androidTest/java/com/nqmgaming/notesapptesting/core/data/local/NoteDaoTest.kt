package com.nqmgaming.notesapptesting.core.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nqmgaming.notesapptesting.core.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
@UninstallModules(AppModule::class)
class NoteDaoTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var noteDb: NoteDb
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        hiltRule.inject()
        noteDao = noteDb.noteDao
    }

    @After
    fun tearDown() {
        noteDb.close()
    }


    @Test
    fun getAllNoteFromEmptyDb_returnNoteListIsEmpty() = runTest {
        assertThat(
            noteDao.getAllNoteEntities().isEmpty()
        ).isTrue()
    }

    @Test
    fun getAllNoteFromEmptyDb_returnNoteListIsNotEmpty() = runTest {
        val note1 = NoteEntity(
            id = 1,
            title = "title 1",
            description = "content 1",
            imageUrl = "image 1",
            dateAdded = System.currentTimeMillis()
        )
        val note2 = NoteEntity(
            id = 2,
            title = "title 2",
            description = "content 2",
            imageUrl = "image 2",
            dateAdded = System.currentTimeMillis()
        )
        noteDao.upsertNoteEntity(noteEntity = note1)
        noteDao.upsertNoteEntity(noteEntity = note2)

        val allNotes = noteDao.getAllNoteEntities()
        assertThat(allNotes).containsExactly(note1, note2)
    }

    @Test
    fun upsertNoteToDatabase_returnNoteIsUpsert() = runTest {
        val originalNote = NoteEntity(
            id = 1,
            title = "title 1",
            description = "content 1",
            imageUrl = "image 1",
            dateAdded = System.currentTimeMillis()
        )
        noteDao.upsertNoteEntity(noteEntity = originalNote)

        val updatedNote = originalNote.copy(title = "updated title")
        noteDao.upsertNoteEntity(noteEntity = updatedNote)

        val retrievedNote = noteDao.getAllNoteEntities().first()
        assertThat(retrievedNote).isEqualTo(updatedNote)
    }

    @Test
    fun deleteNoteFromDatabase_returnNoteIsDelete() = runTest {
        val noteEntity = NoteEntity(
            id = 1,
            title = "title 1",
            description = "content 1",
            imageUrl = "image 1",
            dateAdded = System.currentTimeMillis()
        )
        noteDao.upsertNoteEntity(noteEntity = noteEntity)
        noteDao.deleteNoteEntity(noteEntity = noteEntity)

        val allNotes = noteDao.getAllNoteEntities()
        assertThat(allNotes).doesNotContain(noteEntity)
    }

    @Test
    fun getNoteFromDatabaseById_returnNote() = runTest {
        val noteEntity = NoteEntity(
            id = 1,
            title = "title 1",
            description = "content 1",
            imageUrl = "image 1",
            dateAdded = System.currentTimeMillis()
        )
        noteDao.upsertNoteEntity(noteEntity = noteEntity)

        val retrievedNote = noteDao.getNoteEntityById(id = 1)
        assertThat(retrievedNote).isEqualTo(noteEntity)
    }

}