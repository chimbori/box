package com.chimbori.box.data

import com.chimbori.box.model.AppTab
import com.chimbori.box.model.CollectionItem
import com.chimbori.box.model.FileItem
import com.chimbori.box.model.NoteItem
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable

/**
 * Data source for all ViewModels. The prototype reads hard-coded YAML; a real
 * implementation will replace this with a network-backed repo behind the same interface.
 */
interface BoxRepo {
  suspend fun tabs(): List<AppTab>
  suspend fun files(): List<FileItem>
  suspend fun notes(): List<NoteItem>
  suspend fun collections(): List<CollectionItem>
}

class YamlBoxRepo : BoxRepo {
  private val yaml = Yaml.default

  override suspend fun tabs(): List<AppTab> =
    yaml.decodeFromString(TabsDocument.serializer(), BundledYaml.TABS).tabs

  override suspend fun files(): List<FileItem> =
    yaml.decodeFromString(FilesDocument.serializer(), BundledYaml.FILES).files

  override suspend fun notes(): List<NoteItem> =
    yaml.decodeFromString(NotesDocument.serializer(), BundledYaml.NOTES).notes

  override suspend fun collections(): List<CollectionItem> =
    yaml.decodeFromString(CollectionsDocument.serializer(), BundledYaml.COLLECTIONS).collections
}

@Serializable
private data class TabsDocument(val tabs: List<AppTab>)

@Serializable
private data class FilesDocument(val files: List<FileItem>)

@Serializable
private data class NotesDocument(val notes: List<NoteItem>)

@Serializable
private data class CollectionsDocument(val collections: List<CollectionItem>)
