package com.nqmgaming.notesapptesting.core.di

import android.app.Application
import androidx.room.Room
import com.nqmgaming.notesapptesting.add_note.domain.use_case.SearchImages
import com.nqmgaming.notesapptesting.add_note.domain.use_case.UpsertNote
import com.nqmgaming.notesapptesting.core.data.local.NoteDb
import com.nqmgaming.notesapptesting.core.data.repository.FakeAndroidImagesRepository
import com.nqmgaming.notesapptesting.core.data.repository.FakeAndroidNoteRepository
import com.nqmgaming.notesapptesting.core.domain.repository.ImagesRepository
import com.nqmgaming.notesapptesting.core.domain.repository.NoteRepository
import com.nqmgaming.notesapptesting.note_list.use_case.DeleteNote
import com.nqmgaming.notesapptesting.note_list.use_case.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideNoteDb(application: Application): NoteDb {
        return Room.inMemoryDatabaseBuilder(
            application,
            NoteDb::class.java,
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
    ): NoteRepository {
        return FakeAndroidNoteRepository()
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(
        noteRepository: NoteRepository
    ): GetAllNotes {
        return GetAllNotes(noteRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(
        noteRepository: NoteRepository
    ): DeleteNote {
        return DeleteNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertNoteUseCase(
        noteRepository: NoteRepository
    ): UpsertNote {
        return UpsertNote(noteRepository)
    }


    @Provides
    @Singleton
    fun provideImagesRepository(): ImagesRepository {
        return FakeAndroidImagesRepository()
    }

    @Provides
    @Singleton
    fun provideSearchImageUseCase(
        imagesRepository: ImagesRepository
    ): SearchImages {
        return SearchImages(imagesRepository)
    }

}