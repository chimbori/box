@file:OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)

package com.chimbori.box

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chimbori.box.model.CollectionItem
import com.chimbori.box.model.FileItem
import com.chimbori.box.model.FileType
import com.chimbori.box.model.NoteItem
import com.chimbori.box.theme.LocalSectionModifier
import com.chimbori.box.theme.Section
import com.chimbori.box.theme.boxTypography
import com.chimbori.box.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      BoxTheme {
        BoxApp(remember { AppViewModel() })
      }
    }
  }
}

@Composable
private fun BoxTheme(content: @Composable () -> Unit) {
  val context = LocalContext.current
  val dark = isSystemInDarkTheme()
  val colors = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> if (dark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    dark -> darkColorScheme()
    else -> lightColorScheme()
  }
  MaterialExpressiveTheme(
    colorScheme = colors,
    typography = boxTypography,
    motionScheme = MotionScheme.expressive(),
    content = content,
  )
}

/** Maps icon keys from tabs.yaml to the Android drawables in res/drawable. */
private fun iconFor(key: String): Int = when (key) {
  "notes" -> R.drawable.ic_tab_notes
  "collections" -> R.drawable.ic_tab_collections
  else -> R.drawable.ic_tab_files
}

/** Maps each file type from the shared model to its custom icon in res/drawable. */
private fun fileIconFor(type: FileType): Int = when (type) {
  FileType.FOLDER -> R.drawable.ic_file_folder
  FileType.PDF -> R.drawable.ic_file_pdf
  FileType.SPREADSHEET -> R.drawable.ic_file_spreadsheet
  FileType.IMAGE -> R.drawable.ic_file_image
  FileType.DOCUMENT -> R.drawable.ic_file_document
  FileType.GENERIC -> R.drawable.ic_file_generic
}

@Composable
fun BoxApp(vm: AppViewModel) {
  val tabs by vm.tabs.collectAsState()
  val selectedId by vm.selectedTabId.collectAsState()
  val title by vm.title.collectAsState()
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

  Scaffold(
    modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      LargeFlexibleTopAppBar(
        title = { Text(title) },
        scrollBehavior = scrollBehavior,
      )
    },
    bottomBar = {
      NavigationBar {
        tabs.forEach { tab ->
          NavigationBarItem(
            selected = tab.id == selectedId,
            onClick = { vm.selectTab(tab.id) },
            icon = { Icon(painterResource(iconFor(tab.icon)), contentDescription = tab.title) },
            label = { Text(tab.title) },
          )
        }
      }
    },
  ) { padding ->
    val contentPadding = PaddingValues(
      top = padding.calculateTopPadding(), bottom = padding.calculateBottomPadding(),
    )
    when (selectedId) {
      "files" -> FilesScreen(vm, contentPadding)
      "notes" -> NotesScreen(vm, contentPadding)
      "collections" -> CollectionsScreen(vm, contentPadding)
    }
  }
}

/** Rows are grouped into one Section; each row's shape adapts to its position within the group. */
@Composable
private fun <T> SectionedList(
  data: List<T>,
  contentPadding: PaddingValues,
  row: @Composable (item: T) -> Unit,
) {
  LazyColumn(Modifier.fillMaxSize(), contentPadding = contentPadding) {
    item {
      Section {
        data.forEach { datum ->
          item { row(datum) }
        }
      }
    }
  }
}

/** Shared row styling: transparent so the section's shaped background shows through. */
@Composable
private fun SectionRow(
  headline: @Composable () -> Unit,
  supporting: @Composable (() -> Unit)? = null,
  overline: @Composable (() -> Unit)? = null,
  leading: @Composable () -> Unit,
) {
  ListItem(
    headlineContent = headline,
    supportingContent = supporting,
    overlineContent = overline,
    leadingContent = leading,
    modifier = LocalSectionModifier.current ?: Modifier,
    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
  )
}

@Composable
private fun FilesScreen(vm: AppViewModel, contentPadding: PaddingValues) {
  val items by vm.files.items.collectAsState()
  SectionedList(items, contentPadding) { item: FileItem ->
    SectionRow(
      leading = { Icon(painterResource(fileIconFor(item.type)), contentDescription = null) },
      headline = { Text(item.name) },
      supporting = { Text(listOfNotNull(item.size, item.modified).joinToString(" · ")) },
    )
  }
}

@Composable
private fun NotesScreen(vm: AppViewModel, contentPadding: PaddingValues) {
  val items by vm.notes.items.collectAsState()
  SectionedList(items, contentPadding) { note: NoteItem ->
    SectionRow(
      leading = { Icon(painterResource(R.drawable.ic_tab_notes), contentDescription = null) },
      overline = { Text(note.updated) },
      headline = { Text(note.title) },
      supporting = { Text(note.preview) },
    )
  }
}

@Composable
private fun CollectionsScreen(vm: AppViewModel, contentPadding: PaddingValues) {
  val items by vm.collections.items.collectAsState()
  SectionedList(items, contentPadding) { c: CollectionItem ->
    SectionRow(
      leading = { Icon(painterResource(R.drawable.ic_tab_collections), contentDescription = null) },
      headline = { Text("${c.name} (${c.itemCount})") },
      supporting = { Text(c.description) },
    )
  }
}
