package com.nqmgaming.notesapptesting.core.domain.repository

import com.nqmgaming.notesapptesting.core.domain.model.Images

interface ImagesRepository {

    suspend fun searchImages(query: String): Images?

}