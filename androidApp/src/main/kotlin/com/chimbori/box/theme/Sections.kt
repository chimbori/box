package com.chimbori.box.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

val LocalSectionModifier = compositionLocalOf<Modifier?> { null }

class SectionScope internal constructor() {
  private val items = mutableListOf<Item>()

  /** Adds an item, optionally excluding it from the rendered section. */
  fun item(
    visible: Boolean = true,
    content: @Composable () -> Unit,
  ) {
    items += Item(visible, content)
  }

  internal fun visibleEntries(): List<Item> = items.filter { it.visible }
}

internal class Item(
  val visible: Boolean,
  val content: @Composable () -> Unit,
)

/**
 * Renders visible items with shapes based on their index within the section, laid out in a plain
 * [Column]. Safe to nest inside an outer screen-level `LazyColumn`'s `item { }` block.
 */
@Composable
fun Section(
  modifier: Modifier = Modifier,
  content: SectionScope.() -> Unit,
) {
  val items = SectionScope().apply(content).visibleEntries()
  Column(modifier = modifier) {
    items.forEachIndexed { index, item ->
      CompositionLocalProvider(
        LocalSectionModifier provides Modifier
          .padding(horizontal = 16.dp)
          .sectionShape(index, items.size)
      ) {
        item.content()
      }
    }
  }
}

/** Applies section-aware padding, background, and corner radii to a row. */
@Composable
fun Modifier.sectionShape(index: Int, size: Int): Modifier =
  padding(vertical = 1.dp)
    .clip(
      when {
        size == 1 -> RoundedCornerShape(16.dp)
        index == 0 -> RoundedCornerShape(
          topStart = 16.dp, topEnd = 16.dp,
          bottomStart = 4.dp, bottomEnd = 4.dp
        )
        index == size - 1 -> RoundedCornerShape(
          topStart = 4.dp, topEnd = 4.dp,
          bottomStart = 16.dp, bottomEnd = 16.dp
        )
        else -> RoundedCornerShape(4.dp)
      }
    )
    .background(MaterialTheme.colorScheme.surfaceContainer)
