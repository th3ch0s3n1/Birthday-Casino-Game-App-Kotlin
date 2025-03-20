# Birthday Casino Game App (Kotlin) - Documentation

## Table of Contents
1. [Overview](#1️⃣-overview)
2. [Installation](#2️⃣-installation)
3. [How It Works](#3️⃣-how-it-works)
4. [Game Mechanics](#4️⃣-game-mechanics)
5. [Development Guide](#5️⃣-development-guide)
6. [API & Data Structures](#6️⃣-api--data-structures)
7. [Contributing](#7️⃣-contributing)
8. [License](#8️⃣-license)

---

## 1️⃣ Overview
The **Birthday Casino Game App** is an interactive Android application developed in **Kotlin**, designed to make birthday celebrations fun and engaging. It features **casino-style games**, including a **spinning wheel** where the celebrant can win custom prizes contributed by family and friends.

---

## 2️⃣ Installation
### Prerequisites
- **Android Studio** (latest version recommended)
- **Android SDK 24+**
- **Kotlin and Jetpack Compose**

### Steps
1. **Clone the repository:**
   ```sh
   git clone https://github.com/th3ch0s3n1/Birthday-Casino-Game-App-Kotlin.git
   cd Birthday-Casino-Game-App-Kotlin
   ```
2. **Open in Android Studio**
3. **Sync Gradle dependencies**
4. **Build and run** the app on an emulator or a real device

---

## 3️⃣ How It Works
- Users launch the app and enter a special **casino room**.
- They can **spin the wheel** or play other mini-games to win **prizes**.
- Each prize is **pre-configured** by family and friends before the event.
- Fun sound effects and animations enhance the excitement.

---

## 4️⃣ Game Mechanics
### 🎡 Spin the Wheel
- The wheel is divided into colored segments.
- Each segment represents a **specific prize**.
- Users tap a button to spin the wheel, and it stops on a **random prize**.
- The app detects the winning segment and displays the prize.

---

## 5️⃣ Development Guide
### 📂 Project Structure
```
/app
 ├── src/main/kotlin/dev/tomasek/bdaycasino/
 │   ├── components/         # UI Components (SpinningWheel, Buttons, etc.)
 │   ├── model/              # Data models (WheelSegment, Prize, etc.)
 │   ├── screens/            # Screens
 │   ├── utils/              # Helper functions
 ├── res/
 │   ├── drawable/           # Graphics and icons
 │   ├── raw/                # Sound effects
```

### 🚀 Key Technologies
- **Kotlin & Jetpack Compose** for UI
- **MediaPlayer API** for sound effects
- **State Management** using `remember` and `LaunchedEffect`

---

## 6️⃣ API & Data Structures
### 🎨 WheelSegment Model
```kotlin
data class WheelSegment(
   val label: String,
   val color: Color,
   @RawRes val soundResId: Int,
   val prize: Int
)
```
### 🎯 Prize Model
```kotlin
data class Prize(
    val name: String,
    val description: String,
    val contributor: String
)
```

---

## 7️⃣ Contributing
We welcome contributions! Please follow these steps:
1. **Fork the repository**
2. **Create a feature branch:** `git checkout -b feature-name`
3. **Commit your changes:** `git commit -m "Add new feature"`
4. **Push to your branch:** `git push origin feature-name`
5. **Open a Pull Request**

---

## 8️⃣ License
This project is licensed under the **MIT License**. See the [LICENSE](../LICENSE) file for details.

---

💡 **Have questions or suggestions?** Open an issue or reach out! 🚀

