package com.nqmgaming.notesapptesting.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNoteEntity(noteEntity: NoteEntity)

    @Query("SELECT * FROM noteEntity")
    suspend fun getAllNoteEntities(): List<NoteEntity>

    @Delete
    suspend fun deleteNoteEntity(noteEntity: NoteEntity)

    @Query("SELECT * FROM noteEntity WHERE id = :id")
    suspend fun getNoteEntityById(id: Int): NoteEntity

}
