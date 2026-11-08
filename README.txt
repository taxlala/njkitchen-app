NJ Kitchen - Android APK banane ke steps
========================================
1. Android Studio install karke ek baar kholein (SDK khud download hoga).
2. Zip extract karein. Android Studio > File > Open > ANDAR wala "NJKitchenApp" folder chunein
   (jisme settings.gradle aur app folder dikhte hain) - bahar wala folder NAHI.
   This Window > Trust Project > Gradle sync poora hone dein.
3. Naya UI: upar-left ☰ menu > Build > Generate App Bundles or APKs > Generate APKs
   (purana UI: Build > Build App Bundle(s) / APK(s) > Build APK(s)).
4. Popup me "locate" dabayein, ya ye file kholein:
   NJKitchenApp\app\build\outputs\apk\debug\app-debug.apk
5. APK WhatsApp se bhejein; phone par "Install unknown apps" allow karke install karein.

Error aaye to Build tab ka screenshot bhejein.
Naya version: app\build.gradle me versionCode 1 -> 2 -> 3 karte jaayein.
