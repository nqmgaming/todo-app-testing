package com.nqmgaming.notesapptesting.core.di

import android.app.Application
import androidx.room.Room
import com.nqmgaming.notesapptesting.add_note.domain.use_case.SearchImages
import com.nqmgaming.notesapptesting.add_note.domain.use_case.UpsertNote
import com.nqmgaming.notesapptesting.core.data.local.NoteDb
import com.nqmgaming.notesapptesting.core.data.remote.api.ImagesApi
import com.nqmgaming.notesapptesting.core.data.remote.api.ImagesApi.Companion.BASE_URL
import com.nqmgaming.notesapptesting.core.data.repository.ImagesRepositoryImpl
import com.nqmgaming.notesapptesting.core.data.repository.NoteRepositoryImpl
import com.nqmgaming.notesapptesting.core.domain.repository.ImagesRepository
import com.nqmgaming.notesapptesting.core.domain.repository.NoteRepository
import com.nqmgaming.notesapptesting.note_list.use_case.DeleteNote
import com.nqmgaming.notesapptesting.note_list.use_case.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDb(application: Application): NoteDb {
        return Room.databaseBuilder(
            application,
            NoteDb::class.java,
            "note_db.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteDb: NoteDb
    ): NoteRepository {
        return NoteRepositoryImpl(noteDb)
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
    fun provideImageApi(): ImagesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(
        imagesApi: ImagesApi
    ): ImagesRepository {
        return ImagesRepositoryImpl(imagesApi)
    }

    @Provides
    @Singleton
    fun provideSearchImageUseCase(
        imagesRepository: ImagesRepository
    ): SearchImages {
        return SearchImages(imagesRepository)
    }
}