package com.nqmgaming.notesapptesting.core.data.repository

import com.nqmgaming.notesapptesting.core.domain.model.Images
import com.nqmgaming.notesapptesting.core.domain.repository.ImagesRepository

class FakeAndroidImagesRepository : ImagesRepository {

    private var shouldReturnError = false
    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun searchImages(
        query: String
    ): Images? {
        return if (shouldReturnError) {
            null
        } else {
            Images(
                listOf("image1", "image2", "image3", "image4", "image5", "image6")
            )
        }
    }
}

