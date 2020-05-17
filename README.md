# Syntact

Syntact is an App for learning languages that generates flashcards and organizes them into decks. These flashcards can be learnt and reviewed using a simple spaced repetition algorithm.

## Technologies

* [Kotlin](https://kotlinlang.org/)
* [Dagger 2](https://dagger.dev/) for Dependency Injection
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture), such as [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/guide/navigation)
* [Material Design](https://material.io/)

## Build

### Android Studio

1. Download [Android Studio](https://developer.android.com/studio).
2. Run app on a connected device or running emulator.

### Command Line

1. Clone the project to a folder of your liking.
2. Download the [command line tools](https://developer.android.com/studio#cmdline-tools) and extract into a folder. For future reference, we call this folder `ANDROID_SDK_ROOT`.
2. Install the Android SDK via [sdkmanager](https://developer.android.com/studio/command-line/sdkmanager) by running `sdkmanager "platforms;android-28"`.
3. Create a file in the project root called `local.properties` containing `sdk.dir=PATH_TO_YOUR_ANDROID_SDK_ROOT`, for example:
   * Windows: `sdk.dir=C:\\Android\\sdk`
   * Unix: `sdk.dir=/home/android/sdk`
4. Run `gradlew build`. This will output two APKs:
   * app\build\outputs\apk\debug\app-debug.apk
   * app\build\outputs\apk\release\app-release-unsigned.apk
5. To install the debug-APK on your connected Android device or a running emulator, simply run `gradlew installDebug`. More information: [Build and deploy an APK](https://developer.android.com/studio/build/building-cmdline#build_apk)

## Screenshots

<table><tr>
   <td width="25%"><img src="screenshots/device-2020-05-10-101318.png"/></td>
   <td width="25%"><img src="screenshots/device-2020-05-10-101556.png"/></td>
   <td width="25%"><img src="screenshots/device-2020-05-10-100849.png"/></td>
   <td width="25%"><img src="screenshots/device-2020-05-10-101029.png"/></td>
</tr>
  </table>

## Showcase

<table>

<tr>
    <td width="33%">Tap to + in the main screen to create a new deck. Type a word in either language and tap ADD to generate suggestions for sentences containing that word.</td>
    <td width="33%">Tapping on a suggestion allows you to delete it or use its sentences' words as additional input words.</td>
  <td width="33%">Finalize your deck by naming it and deciding how many new cards you want to be shown a day.</td>
</tr>
<tr>
   <td width="33%"><img src="img/01_create_deck.gif"/></td>
   <td width="33%"><img src="img/03_create_deck.gif"/></td>
   <td width="33%"><img src="img/02_create_deck.gif"/></td>
</tr>
</table>

<table>

<tr>
    <td width="33%">Start learning by pressing START and typing in the solution. A minimum 90% accordance with the correct solution counts as a correct try, and as a failure otherwise.</td>
    <td width="33%">Tap OPTIONS on a deck to see its details, revealing each card's next due date and the opportunity to adjust deck settings.</td>
  <td width="33%">Toggle Dark Mode with three different options: on, off or automatic.</td>
</tr>
<tr>
   <td width="33%"><img src="img/04_play.gif"/></td>
   <td width="33%"><img src="img/05_deck_details.gif"/></td>
   <td width="33%"><img src="img/06_night_mode.gif"/></td>
</tr>
</table>

