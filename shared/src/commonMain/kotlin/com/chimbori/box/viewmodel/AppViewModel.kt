package com.chimbori.box.viewmodel

import com.chimbori.box.data.BoxRepo
import com.chimbori.box.data.YamlBoxRepo
import com.chimbori.box.model.AppTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** Shell for the app: owns the bottom tabs, the selected tab, the title, and one ViewModel per top-level screen. */
class AppViewModel(private val repo: BoxRepo) : ViewModel() {
  constructor() : this(YamlBoxRepo())

  private val _tabs = MutableStateFlow<List<AppTab>>(emptyList())
  val tabs: StateFlow<List<AppTab>> = _tabs.asStateFlow()

  private val _selectedTabId = MutableStateFlow("")
  val selectedTabId: StateFlow<String> = _selectedTabId.asStateFlow()

  /** Name of the selected tab, shown at the top of the screen. */
  val title: StateFlow<String> = combine(_tabs, _selectedTabId) { tabs, id ->
    tabs.firstOrNull { it.id == id }?.title.orEmpty()
  }.stateIn(scope, SharingStarted.Eagerly, "")

  val files = FilesViewModel(repo)
  val notes = NotesViewModel(repo)
  val collections = CollectionsViewModel(repo)

  init {
    scope.launch {
      val loaded = repo.tabs()
      _tabs.value = loaded
      if (_selectedTabId.value.isEmpty()) _selectedTabId.value = loaded.firstOrNull()?.id.orEmpty()
    }
  }

  fun selectTab(id: String) {
    if (_tabs.value.any { it.id == id }) _selectedTabId.value = id
  }
}
