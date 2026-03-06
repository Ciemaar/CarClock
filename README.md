# Infiniti QX60 Dashboard Clock

A custom-designed, distraction-optimized Android Automotive OS app built to run natively on the infotainment screen of a 2026 Infiniti QX60.

This app features both an analog and digital clock face, an optional date display, and a simplified pure black-and-white dark mode for nighttime driving with subtle dark green accents (Infiniti brand colors).

## Features
- **Distraction Optimized**: The app includes the required `android:name="distractionOptimized"` metadata, allowing it to remain visible while the vehicle is in motion.
- **Automotive Native**: Uses `<uses-feature android:name="android.hardware.type.automotive" />` to target native car hardware rather than Android Auto projection from a phone.
- **Jetpack Compose UI**: Built with modern Android UI tools, ensuring smooth animations for the analog second hand.
- **Dark/Light Themes**: Automatically responds to the car's day/night headlight sensor. Dark mode shifts to pure black/white to prevent dashboard glare.

---

## 🎨 Demonstration & UI Flow

### Analog Face
By default, the app opens to a clean, elegant **Analog Clock**. The second hand smoothly ticks around the dial. The hand color is gray with a dark Infiniti Green accent.

### Digital Face
Tapping the screen opens the settings menu, allowing you to switch to the **Digital Clock**. It displays the time in a large, readable format (e.g., "2:30 PM").

### Interactions
- **Tap anywhere** on the background to open the Settings Menu.
- Toggle between **Analog** and **Digital** clocks.
- Toggle the **Date** display (shows e.g., "Thursday, January 1" below the clock).
- Tap the **Close** button (styled in Infiniti Green) to return to the clock.

### 📸 How to Capture Real Screenshots
To capture actual, high-quality screenshots for the Google Play Store or to replace these placeholders:

**Option A: From Android Studio (Easiest)**
1. Launch the app in the **Android Automotive Emulator**.
2. Click the **Camera Icon** (Take Screenshot) in the emulator's side toolbar.
3. The screenshot will automatically save to your Desktop or a specified folder in high resolution.

**Option B: From the Physical Vehicle (via adb)**
If you sideloaded the app via a laptop connected to the car:
1. Ensure `adb` is connected to the vehicle.
2. Navigate to the screen you want to capture on the dashboard.
3. Run this command in your terminal: `adb exec-out screencap -p > clock_screenshot.png`
4. The high-quality PNG will be saved directly to your computer.

### Screenshots Placeholder
*(When publishing, replace these mockups with your captured images. The required aspect ratio for Automotive apps is typically 1024x768 or widescreen landscape).*

```
+--------------------------------------------------+
|                                                  |
|                                                  |
|                        12                        |
|                     /      \                     |
|                   9    ()    3                   |
|                     \      /                     |
|                        6                         |
|                                                  |
|               Thursday, January 1                |
|                                                  |
+--------------------------------------------------+
```
*(Synthetic mockup of the Analog face with Date enabled)*

```
+--------------------------------------------------+
|                                                  |
|                                                  |
|                                                  |
|                                                  |
|                   2 : 3 0                        |
|                      P M                         |
|                                                  |
|               Thursday, January 1                |
|                                                  |
|                                                  |
+--------------------------------------------------+
```
*(Synthetic mockup of the Digital face with Date enabled)*

```
+--------------------------------------------------+
|              Clock Settings                      |
|                                                  |
|  Clock Type   Digital  [ ==O ]  Analog           |
|                                                  |
|  Show Date    Off      [ ==O ]  On               |
|                                                  |
|                  [  Close  ]                     |
+--------------------------------------------------+
```
*(Synthetic mockup of the interactive Settings overlay)*

---

## 🏎️ How to Test and Run the App

Because this is a native Android Automotive OS application, you cannot test it on a standard Android phone or tablet. You must use an Automotive Emulator or a physical vehicle dashboard.

### 1. Using Android Studio (Recommended)
1. Download and install [Android Studio](https://developer.android.com/studio).
2. Open Android Studio and select **File > Open**, then choose this project directory.
3. Wait for Gradle to sync the project.
4. Go to **Tools > Device Manager** and click **Create device**.
5. In the left panel, select the **Automotive** category.
6. Choose a profile (e.g., *Automotive (1024p landscape)*) and click **Next**.
7. Download a system image. For a 2026 vehicle, select **API Level 33 or 34** (Android 13/14) and click **Next**, then **Finish**.
8. Click the green **Run** button (`Shift + F10`) in the top toolbar to launch the app on your new Automotive emulator.

### 2. Running Automated Tests
The application includes both logic tests and UI tests.
- To run logic formatting tests: `./gradlew testDebugUnitTest`
- To run Jetpack Compose UI interaction tests on a connected device/emulator: `./gradlew connectedAndroidTest`

### 3. Sideloading via USB (Physical Vehicle)
If your car allows developer mode and USB debugging:
1. Compile the APK by running `./gradlew assembleDebug` in the terminal.
2. The APK will be generated at `app/build/outputs/apk/debug/app-debug.apk`.
3. Enable "Developer Options" on your car's Android Automotive OS unit (usually by tapping the "Build Number" in Settings 7 times).
4. Enable "USB Debugging".
5. Connect your laptop to the car's data USB port.
6. Run `adb install app/build/outputs/apk/debug/app-debug.apk` from your laptop terminal.

---

## 🚀 Publishing to the Google Play Store

Publishing an Android Automotive OS app requires a few extra steps compared to a standard mobile app to ensure driver safety.

### 1. App Categorization
Google Play strictly limits the *types* of apps allowed on Automotive OS. Supported categories generally include Media, Navigation, Point of Interest (POI), Video, IoT, and Weather.

*Note: A standalone "Clock" or "Utility" app is not currently an officially supported sub-category for independent publishing unless packaged as an IoT or POI app. You may need to distribute this app privately to your vehicle or use internal testing tracks if Google Play rejects the category.*

### 2. Safety Guidelines (Distraction Optimization)
Your app is already configured as "Distraction Optimized" in the `AndroidManifest.xml`. This means Google reviewers will rigorously test the app against the [Driver Distraction Guidelines (DDG)](https://source.android.com/docs/automotive/driver_distraction/guidelines).
- **Rule of Thumb**: The app must not require the driver to read long text, watch complex animations, or interact heavily while driving.
- Because this app is just a clock that updates once a second, it complies with the baseline safety rules.

### 3. Submission Process
1. Log in to the [Google Play Console](https://play.google.com/console).
2. Click **Create app** and fill in the app details.
3. Navigate to **Setup > Advanced settings > Form factors**.
4. Click **Add form factor** and select **Android Automotive OS**.
5. Upload your Release APK or App Bundle (`.aab`) to an Internal Testing track or Production track.
6. Google will ask you to provide specific screenshots taken from an Automotive layout (e.g., landscape 1080p).
7. Submit the app for review. The review process for Automotive apps takes longer than mobile apps because it involves manual safety testing.