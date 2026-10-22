package com.chimbori.box.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/** Base class for shared ViewModels. Work runs on the main dispatcher so state can drive UI directly. */
abstract class ViewModel {
  protected val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

  /** Calls [onChange] with each new value of [flow], starting with the current value. Used by Swift and JS bridges. */
  fun <T> observe(flow: StateFlow<T>, onChange: (T) -> Unit): Job =
    scope.launch { flow.collect { onChange(it) } }

  fun clear() {
    scope.cancel()
  }
}
