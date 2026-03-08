# Developer Guide - Infiniti QX60 Clock App

This project is an Android Automotive OS standalone app (API 33+) written in Kotlin using Jetpack Compose. It runs natively on the infotainment unit, _not_ as an Android Auto phone projection.

## 🏗️ Technical Architecture

### Tech Stack

- **Language**: Kotlin 1.9
- **UI Toolkit**: Jetpack Compose (`androidx.compose:compose-bom:2024.02.00`)
- **Minimum SDK**: API 29 (Android 10)
- **Target SDK**: API 34 (Android 14)
- **Build System**: Gradle 8.8

### Key Components

1. **`MainActivity.kt`**: The entry point. Handles the top-level Compose state for toggling the settings overlay, and listens to the system dark mode sensor to apply `InfinitiClockTheme`.
2. **`ClockComponents.kt`**: Contains the complex rendering for `AnalogClock` (using `Canvas`, `drawCircle`, `drawLine` with trigonometry for sweeping hands) and `DigitalClock`. It uses `LaunchedEffect` and `delay()` coroutines for its ticking mechanism instead of a `Timer` thread, binding the tick loop tightly to the Composable lifecycle.
3. **`SettingsOverlay.kt`**: The state-backed, transparent overlay modal containing Jetpack Compose `Switch` buttons.
4. **`AndroidManifest.xml`**: Critically configured for Automotive OS:
   - `<uses-feature android:name="android.hardware.type.automotive" android:required="true" />`
   - `<meta-data android:name="distractionOptimized" android:value="true" />` attached to the `MainActivity`.

---

## 🛠️ Testing the Application

### Automated Unit & UI Tests

- **Logic Tests**: (`app/src/test/java/com/infiniti/clock/ClockLogicTest.kt`) Validates that the date and time strings parse perfectly using `SimpleDateFormat`.
  Run: `./gradlew testDebugUnitTest`
- **Compose UI Tests**: (`app/src/androidTest/java/com/infiniti/clock/ClockUITest.kt`) Checks that the Jetpack Compose nodes, buttons, and switches render accurately.
  Run: `./gradlew connectedAndroidTest` (requires a connected device or emulator)

### Emulator Setup (Android Studio)

Because this app relies on the `android.hardware.type.automotive` hardware constraint, it will _fail_ to install on a regular Android phone emulator.

1. In Android Studio, open **Device Manager**.
2. Click **Create device** -> Select the **Automotive** tab.
3. Choose the **Automotive (1024p landscape)** profile.
4. Download a system image for API Level 33 or 34 (Android 13/14).
5. Launch the emulator and run the app from Android Studio.

---

## 🚗 Deployment & Sideloading via USB

To install this directly onto the QX60 dashboard (if developer mode is allowed by the OEM):

1. Build the APK: `./gradlew assembleDebug`
2. Enable "Developer Options" on the car's Android unit (tap "Build Number" 7 times in Settings).
3. Enable "USB Debugging".
4. Connect your laptop to the car's data USB port.
5. Sideload the APK: `adb install app/build/outputs/apk/debug/app-debug.apk`

---

## 📸 Capturing Store Screenshots

You must capture high-resolution images in a landscape aspect ratio (e.g., 1024x768) to publish to Google Play.

**Method 1: Automotive Emulator (Easiest)**

1. Launch the app in the Android Studio Automotive Emulator.
2. Click the **Camera Icon** in the right-hand emulator toolbar.
3. The high-res screenshot saves to your Desktop.

**Method 2: Physical Vehicle (via adb)**

1. With your laptop connected to the car via USB, navigate to the clock screen.
2. Run: `adb exec-out screencap -p > clock_screenshot.png`
3. The dashboard image will save locally as a PNG.

---

## 🚀 Play Store Publishing & Automotive Constraints

Publishing a native Automotive app requires specific configurations:

### Driver Distraction Guidelines (DDG)

Google strictly limits what can run while driving. Because your `AndroidManifest.xml` marks this app as `distractionOptimized=true`, human reviewers at Google will heavily scrutinize the UI.

- **Rule**: The app cannot require scrolling long text, complex animations (like videos), or intricate inputs while driving.
- **Compliance**: This app safely meets DDG requirements because it relies on passive glanceability (a ticking clock) and minimal tap targets (a simple overlay).

### App Categorization limitations

Google Play strictly limits Automotive apps to pre-approved categories (e.g., Media, Navigation, IoT, Video).
**Important Note:** A standalone "Clock" or "Utility" app is not currently an officially supported category for independent public distribution.

- You may need to distribute this app privately to your specific Google Account.
- Alternatively, you can use **Internal Testing tracks** on the Play Console to deploy the app directly to your vehicle without undergoing a public category review.

### Submission Steps

1. Log in to the [Google Play Console](https://play.google.com/console).
2. Go to **Setup > Advanced settings > Form factors**.
3. Click **Add form factor** and select **Android Automotive OS**.
4. Upload your generated `.aab` (App Bundle) to an Internal Testing track.
5. Provide the requested Automotive-resolution screenshots and await review.
