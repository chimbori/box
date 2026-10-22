# Box

Kotlin Multiplatform project with shared code for Android, iOS, macOS, and the web.

The root `settings.gradle.kts` and `build.gradle.kts` are the Gradle build entry points, and the
project uses the Gradle wrapper (`./gradlew`), so no separate Gradle install is needed.

Build the whole project with `./gradlew build`.

## Running

[Task](https://taskfile.dev) runs each platform from `Taskfile.yml`:

- `task android`: install and launch the Android app on a connected device or emulator.
- `task ios`: build and launch the iOS app in the Simulator.
- `task macos`: build and launch the macOS app.
- `task apple`: run `ios` and `macos` in parallel.
- `task web`: start the web dev server.
- `task preview`: run Android and Apple in parallel, then start the web dev server last so it keeps running.

## Modules

- [/androidApp](./androidApp) contains the Android application. Run it with `./gradlew :androidApp:installDebug`.
- [/webApp](./webApp) contains the React web app, which uses the JS build of `:shared`. Run it with `task web`, which builds the shared library, installs npm dependencies, and starts Vite on port 8081.
- [/iosApp](./iosApp) contains the iOS and macOS applications, built with Xcode. Open `iosApp/iosApp.xcodeproj`. The Xcode build phase invokes `./gradlew :shared:embedAndSignAppleFrameworkForXcode`.
- [/shared](./shared) holds the code shared across the applications. [src/commonMain](./shared/src/commonMain)
  is for common code; the sibling `src/<platform>Main` folders (for example `src/androidMain`) hold
  code compiled only for that platform.
- Shared layers: `model/` holds data classes, `data/` holds the `BoxRepo` interface and `YamlBoxRepo`, and
  `viewmodel/` holds ViewModels that drive all UI. Per-tab ViewModels are separate per screen. The prototype's
  data comes from [yaml](./shared/src/commonMain/yaml) files, bundled into Kotlin at build time. A real
  implementation would replace `YamlBoxRepo` with a network-backed repo.
- The JS target exports TypeScript definitions for use in React apps. Build them with
  `./gradlew :shared:jsBrowserProductionLibrary`, which writes
  `shared/build/dist/js/productionLibrary/Box-shared.d.ts`.
