# ComposeComponentUtils

This is a Kotlin Multiplatform UI component library project.

## Project Structure

* [/ComposeComponentUtils](./ComposeComponentUtils/src) is the main UI component library.
  - [commonMain](./ComposeComponentUtils/src/commonMain/kotlin) contains the shared Compose components.
* [/sample](./sample/src) is the sample application demonstrating the library across different platforms.
  - Targets: Android, iOS, Web, Desktop (JVM).
* [/server](./server/src/main/kotlin) is for the Ktor server application.

### Build and Run Sample Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on Windows
  ```shell
  .\gradlew.bat :sample:assembleDebug
  ```

### Build and Run Desktop (JVM) Application
- on Windows
  ```shell
  .\gradlew.bat :sample:run
  ```

### Build and Run Server
- on Windows
  ```shell
  .\gradlew.bat :server:run
  ```

### Build and Run Web Application
- for the Wasm target:
  - on Windows
    ```shell
    .\gradlew.bat :sample:wasmJsBrowserDevelopmentRun
    ```
- for the JS target:
  - on Windows
    ```shell
    .\gradlew.bat :sample:jsBrowserDevelopmentRun
    ```
