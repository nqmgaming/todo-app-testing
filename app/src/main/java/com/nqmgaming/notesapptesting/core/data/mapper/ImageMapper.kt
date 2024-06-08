package com.nqmgaming.notesapptesting.core.data.mapper

import com.nqmgaming.notesapptesting.core.data.remote.dto.ImageListDto
import com.nqmgaming.notesapptesting.core.domain.model.Images

fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { imageDto ->
            imageDto.previewURL ?: ""
        } ?: emptyList()
    )
}
