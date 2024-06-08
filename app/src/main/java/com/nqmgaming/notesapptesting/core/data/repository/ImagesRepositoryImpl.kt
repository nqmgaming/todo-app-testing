package com.nqmgaming.notesapptesting.core.data.repository

import com.nqmgaming.notesapptesting.core.data.mapper.toImages
import com.nqmgaming.notesapptesting.core.data.remote.api.ImagesApi
import com.nqmgaming.notesapptesting.core.domain.model.Images
import com.nqmgaming.notesapptesting.core.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesApi: ImagesApi
) : ImagesRepository {
    override suspend fun searchImages(query: String): Images? {
        return imagesApi.searchImages(query)?.toImages()
    }
}