# Box

Kotlin Multiplatform project with shared code for Android, iOS, macOS, and the web.

The root `settings.gradle.kts` and `build.gradle.kts` are the Gradle build entry points, and the
project uses the Gradle wrapper (`./gradlew`), so no separate Gradle install is needed.

Build the whole project with `./gradlew build`.

## Modules

- [/androidApp](./androidApp) contains the Android application. Run it with `./gradlew :androidApp:installDebug`.
- [/webApp](./webApp) contains the React web app, which uses the JS build of `:shared`. Run it with `./gradlew webDev`, which builds the shared library, installs npm dependencies, and starts Vite on port 8081.
- [/iosApp](./iosApp) contains the iOS and macOS applications, built with Xcode. Open `iosApp/iosApp.xcodeproj`. The Xcode build phase invokes `./gradlew :shared:embedAndSignAppleFrameworkForXcode`.
- [/shared](./shared) holds the code shared across the applications. [src/commonMain](./shared/src/commonMain)
  is for common code; the sibling `src/<platform>Main` folders (for example `src/androidMain`) hold
  code compiled only for that platform.
- The JS target exports TypeScript definitions for use in React apps. Build them with
  `./gradlew :shared:jsBrowserProductionLibrary`, which writes
  `shared/build/dist/js/productionLibrary/Box-shared.d.ts`.
