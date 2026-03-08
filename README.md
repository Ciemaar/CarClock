# Infiniti QX60 Dashboard Clock

A custom-designed, distraction-optimized Android Automotive OS app built to run natively on the infotainment screen of a 2026 Infiniti QX60.

This app features both an analog and digital clock face, an optional date display, and a simplified pure black-and-white dark mode for nighttime driving with subtle dark green accents (Infiniti brand colors).

## 📖 Documentation Quick Links

To help you get started, we have split the documentation into two guides:

*   **[User Guide](USER_GUIDE.md)**: A manual detailing how to interact with the clock in the Infiniti QX60, switch between analog/digital modes, toggle the date, and understand the day/night theme behavior.
*   **[Developer Guide](DEVELOPER_GUIDE.md)**: A technical guide covering how to compile the app, run automated tests, use the Android Studio Automotive Emulator, sideload the app via USB (`adb`), capture high-resolution screenshots, and publish to the Google Play Store while adhering to driver distraction guidelines.

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

### Screenshots Placeholder
*(Please refer to the [Developer Guide](DEVELOPER_GUIDE.md) for instructions on how to generate and replace these mockups with real device screenshots from your car or emulator).*

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

## 🏎️ Key Features Overview
- **Distraction Optimized**: The app includes the required `android:name="distractionOptimized"` metadata, allowing it to remain visible while the vehicle is in motion.
- **Automotive Native**: Uses `<uses-feature android:name="android.hardware.type.automotive" />` to target native car hardware rather than Android Auto projection from a phone.
- **Jetpack Compose UI**: Built with modern Android UI tools, ensuring smooth 60fps animations for the analog second hand.
- **Dark/Light Themes**: Automatically responds to the car's day/night headlight sensor. Dark mode shifts to pure black/white to prevent dashboard glare.