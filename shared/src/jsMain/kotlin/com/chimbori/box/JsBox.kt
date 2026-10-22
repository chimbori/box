package com.chimbori.box

import com.chimbori.box.model.CollectionItem
import com.chimbori.box.model.FileItem
import com.chimbori.box.model.NoteItem
import com.chimbori.box.model.AppTab
import com.chimbori.box.viewmodel.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@Serializable
private data class BoxState(
  val tabs: List<AppTab>,
  val selectedTabId: String,
  val title: String,
  val files: List<FileItem>,
  val notes: List<NoteItem>,
  val collections: List<CollectionItem>,
)

/** Web entry point. Exposes the shared AppViewModel as JSON state plus a change listener for React. */
@OptIn(ExperimentalJsExport::class)
@JsExport
class JsBox {
  private val app = AppViewModel()
  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
  private var latestJson: String = "{}"
  private var listener: (() -> Unit)? = null

  init {
    scope.launch {
      combine(
        app.tabs, app.selectedTabId, app.title,
        app.files.items, app.notes.items, app.collections.items,
      ) { values ->
        @Suppress("UNCHECKED_CAST")
        BoxState(
          tabs = values[0] as List<AppTab>,
          selectedTabId = values[1] as String,
          title = values[2] as String,
          files = values[3] as List<FileItem>,
          notes = values[4] as List<NoteItem>,
          collections = values[5] as List<CollectionItem>,
        )
      }.collect { state ->
        latestJson = Json.encodeToString(BoxState.serializer(), state)
        listener?.invoke()
      }
    }
  }

  fun stateJson(): String = latestJson

  fun selectTab(id: String) = app.selectTab(id)

  fun onChange(callback: () -> Unit) {
    listener = callback
    callback()
  }
}
