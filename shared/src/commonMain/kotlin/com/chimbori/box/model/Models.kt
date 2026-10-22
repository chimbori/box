package com.chimbori.box.model

import kotlinx.serialization.Serializable

@Serializable
data class AppTab(
  val id: String,
  val title: String,
  val icon: String,
)

@Serializable
data class FileItem(
  val name: String,
  val kind: String,
  val size: String? = null,
  val modified: String,
) {
  /** Derived from the name's extension; folders are identified by [kind]. */
  val type: FileType
    get() {
      if (kind == "folder") return FileType.FOLDER
      return when (name.substringAfterLast('.', "").lowercase()) {
        "pdf" -> FileType.PDF
        "xls", "xlsx", "csv", "numbers" -> FileType.SPREADSHEET
        "png", "jpg", "jpeg", "gif", "heic", "webp", "svg" -> FileType.IMAGE
        "doc", "docx", "txt", "md", "rtf", "pages" -> FileType.DOCUMENT
        else -> FileType.GENERIC
      }
    }
}

enum class FileType { FOLDER, PDF, SPREADSHEET, IMAGE, DOCUMENT, GENERIC }

@Serializable
data class NoteItem(
  val title: String,
  val preview: String,
  val updated: String,
)

@Serializable
data class CollectionItem(
  val name: String,
  val description: String,
  val itemCount: Int,
)
