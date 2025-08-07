# 📅 OnThisDay - Kotlin Multiplatform Wikipedia Extract Viewer

A Kotlin Multiplatform app that fetches and displays historical events from Wikipedia using the [Wikipedia API](https://en.wikipedia.org/w/api.php). Built using Jetpack Compose for Android, iOS and Desktop.

## Features

-  Fetches Wikipedia extracts using Ktor client
-  Clean architecture with domain, data, and presentation layers
-  Jetpack Compose for UI
-  Koin for Dependency Injection
-  Multiplatform support (`androidMain`, `iosMain`, `jvmMain`)
-  Error and loading UI states
-  Well-organized folder structure

## Project Structure

```
composeApp/
├── build/
├── release/
├── src/
│   ├── androidMain/                    # Android-specific code
│   ├── iosMain/                        # iOS-specific code
│   ├── jvmMain/                        # JVM target
│   └── commonMain/                     # Shared business logic
│       ├── composeResources/
│       └── kotlin/
│           └── com/abhay/onthisday/
│               ├── app/
│               │   ├── App.kt
│               │   └── Routes.kt
│               ├── data/
│               │   ├── dto/
│               │   ├── mapper/
│               │   ├── network/client/
│               │   │   ├── HttpClientFactory.kt
│               │   │   ├── KtorRemoteDataSource.kt
│               │   │   └── RemoteDataSource.kt
│               │   └── repository/
│               ├── di/
│               │   ├── initKoin.kt
│               │   └── Modules.kt
│               ├── domain/
│               │   ├── model/
│               │   ├── repository/
│               │   └── util/
│               └── presentation/
│                   ├── components/
│                   │   ├── ErrorContent.kt
│                   │   └── LoadingContent.kt
│                   ├── details/
│                   │   ├── DetailsScreen.kt
│                   │   ├── DetailsViewModel.kt
│                   │   └── ParsingDetailUtil.kt
│                   ├── home_screen/
│                   ├── ui/
│                   └── util/
│                       └── DataErrorToStringResource.kt
├── gradle/
├── iosApp/
│   ├── Configuration/
│   ├── iosApp/
│   └── iosApp.xcodeproj/
├── .gitignore
└── build.gradle.kts
```

## Shared Code Architecture

### Common Code Structure

-  **`/composeApp/src/commonMain/kotlin`** - Code shared across all platforms
-  **`/composeApp/src/androidMain/kotlin`** - Android-specific implementations
-  **`/composeApp/src/iosMain/kotlin`** - iOS-specific implementations
-  **`/composeApp/src/jvmMain/kotlin`** - Desktop/JVM-specific code

### iOS Application

-  **`/iosApp/iosApp`** - Contains iOS application entry point
-  Even when sharing UI with Compose Multiplatform, this entry point is required for iOS

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)

## Screenshots

### Android

<div style="display: flex; gap: 16px;">
  <img src="./screenshots/android-1.jpg" alt="Android Screenshot 1" width="300"/>
  <img src="./screenshots/android-2.jpg" alt="Android Screenshot 2" width="300"/>
</div>


## iOS
<img src="./screenshots/ios.png" alt="iOS Screenshot" width="300"/>

## Getting Started

### Prerequisites

-  Android Studio (for Android development)
-  Xcode (for iOS development)
-  Kotlin Multiplatform Mobile plugin

### 1. Clone the Repository

```bash
git clone https://github.com/abhayy08/On-This-Day-Compose-Multiplatform-.git
cd onthisday
```

### 2. Run on Android

1. Open the project in Android Studio
2. Select the Android target
3. Click the **Run** button

### 3. Run on iOS

1. Set up `iosApp` run configuration in Android Studio
2. Select `iosApp` target in Android Studio
3. Press the **Run** button

### 4. Run on Desktop

1. Run `./gradlew run` on the terminal for launching the app on Desktop.


## Tech Stack

| Layer                    | Technology                                  |
| ------------------------ | ------------------------------------------- |
| **UI**                   | Jetpack Compose                             |
| **Networking**           | Ktor Client                                 |
| **Dependency Injection** | Koin                                        |
| **Architecture**         | MVVM                                        |
| **Shared Code**          | Kotlin Multiplatform(Compose Multiplatform) |

---
