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
        )
    }

}