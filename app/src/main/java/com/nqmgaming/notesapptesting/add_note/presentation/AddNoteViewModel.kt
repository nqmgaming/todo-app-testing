package com.nqmgaming.notesapptesting.add_note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqmgaming.notesapptesting.add_note.domain.use_case.SearchImages
import com.nqmgaming.notesapptesting.add_note.domain.use_case.UpsertNote
import com.nqmgaming.notesapptesting.add_note.presentation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val upsertNote: UpsertNote,
    private val searchImages: SearchImages
) : ViewModel() {
    private val _addNoteState = MutableStateFlow(AddNoteState())
    val addNoteState = _addNoteState.asStateFlow()

    private val _noteSaveChannel = Channel<Boolean>()
    val noteSaveChannel = _noteSaveChannel.receiveAsFlow()

    fun onAction(actions: AddNoteActions) {
        when (actions) {
            is AddNoteActions.PickImage -> {
                _addNoteState.update { it.copy(imageUrl = actions.imageUrl) }
            }

            AddNoteActions.SaveNote -> {
                viewModelScope.launch {
                    val isSaved = saveNote(
                        addNoteState.value.title,
                        addNoteState.value.description,
                        addNoteState.value.imageUrl
                    )
                    _noteSaveChannel.send(isSaved)
                }
            }

            AddNoteActions.UpdateImagesDialogVisibility -> {
                _addNoteState.update { it.copy(isImagesDialogShowing = !addNoteState.value.isImagesDialogShowing) }
            }

            is AddNoteActions.UpdateSearchImageQuery -> {
                _addNoteState.update { it.copy(searchImagesQuery = actions.newQuery) }
                searchImages(actions.newQuery)
            }

            is AddNoteActions.UpdateTitle -> {
                _addNoteState.update { it.copy(title = actions.newTitle) }
            }

            is AddNoteActions.UpdateDescription -> {
                _addNoteState.update { it.copy(description = actions.newDescription) }

            }
        }
    }

    suspend fun saveNote(
        title: String,
        description: String,
        imageUrl: String
    ): Boolean {
        val isSaved = upsertNote(title, description, imageUrl)
        return isSaved
    }


    private var searchJob: Job? = null

    fun searchImages(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)

            searchImages.invoke(query)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _addNoteState.update { it.copy(imageList = emptyList()) }
                        }

                        is Resource.Loading -> {
                            _addNoteState.update { it.copy(isLoadingImages = result.isLoading) }
                        }

                        is Resource.Success -> {
                            _addNoteState.update {
                                it.copy(imageList = result.data?.images ?: emptyList())
                            }
                        }
                    }
                }
        }

    }
}