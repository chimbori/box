package com.chimbori.box.viewmodel

import com.chimbori.box.data.BoxRepo
import com.chimbori.box.model.CollectionItem
import com.chimbori.box.model.FileItem
import com.chimbori.box.model.NoteItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/** Content for the Files tab. */
class FilesViewModel(private val repo: BoxRepo) : ViewModel() {
  private val _items = MutableStateFlow<List<FileItem>>(emptyList())
  val items: StateFlow<List<FileItem>> = _items.asStateFlow()

  init {
    scope.launch { _items.value = repo.files() }
  }
}

/** Content for the Notes tab. */
class NotesViewModel(private val repo: BoxRepo) : ViewModel() {
  private val _items = MutableStateFlow<List<NoteItem>>(emptyList())
  val items: StateFlow<List<NoteItem>> = _items.asStateFlow()

  init {
    scope.launch { _items.value = repo.notes() }
  }
}

/** Content for the Collections tab. */
class CollectionsViewModel(private val repo: BoxRepo) : ViewModel() {
  private val _items = MutableStateFlow<List<CollectionItem>>(emptyList())
  val items: StateFlow<List<CollectionItem>> = _items.asStateFlow()

  init {
    scope.launch { _items.value = repo.collections() }
  }
}
