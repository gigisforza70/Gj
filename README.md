<p align="center">
  <img src="https://raw.githubusercontent.com/kiwibrowser/src.next/kiwi/kiwi_logo_circle.svg" alt="Kiwi Browser Revived"
	title="Kiwi Browser Revived" width="160" height="160"/>
</p>

# Kiwi Browser (Revived)

Kiwi Browser is an open-source, fast Chromium-based browser for Android with **full Chrome Extensions support**, bottom address bar, AMOLED night mode, and advanced customization.

This repository continues the active development and maintenance of Kiwi Browser following the 2025 archive announcement, providing modern Android (Android 14/15, HyperOS) compatibility, critical crash fixes, UI modernization, and regular security updates.

---

## 🚀 Key Improvements & Modern Features

* **Address Bar (Omnibox) Crash Fix:**
  * Fixed the critical crash on Android 14 / HyperOS tablets (e.g. Xiaomi Pad 6) when tapping the address bar.
  * Added defensive exception guards against `SecurityException` during clipboard zero-suggestions and `ClassCastException` on tablet layout transitions.
* **Modern Chrome Web Store Support:**
  * Added native compatibility for the new `chromewebstore.google.com` domain for seamless one-click extension installations.
* **Refreshed Material 3 New Tab Page (`modern_ntp.html`):**
  * Modern glassmorphic cards, rounded search bar, and instant engine switching (Google, DuckDuckGo, Bing, Brave, AI Assistant).
  * Direct quick-launch shortcuts for Chrome Web Store, Extensions, Bookmarks, and Downloads.
* **144Hz & High-Refresh Tablet Performance:**
  * Improved animation handling and UI thread responsiveness for smooth 120Hz/144Hz displays.
* **Standalone Build Pipeline:**
  * Replaced defunct remote build servers with automated standalone GitHub Actions (`build_kiwi_standalone.yml`) and local build runner (`build_kiwi.sh`).

---

## 🛠️ Building Kiwi Browser Locally

Kiwi Browser is built using Chromium's build system with Google's `depot_tools`, `gn`, and `ninja`.

### Prerequisites
* Linux (Ubuntu 20.04 / 22.04) or Windows via **WSL2**
* Python 3, OpenJDK (JDK 8 or 11), Ninja, and Ccache:
  ```bash
  sudo apt-get update
  sudo apt-get install -y python3 python-is-python3 openjdk-11-jdk-headless ninja-build ccache curl git
  ```

### Build Steps
1. Clone this repository:
   ```bash
   git clone -b kiwi-revived https://github.com/kiwibrowser/src.next.git
   cd src.next
   ```
2. Run the automated build script:
   ```bash
   chmod +x build_kiwi.sh
   ./build_kiwi.sh arm64
   ```
   *(Supported architectures: `arm64`, `arm`, `x64`, `x86`)*

3. Output APK will be generated at:
   ```
   out/android_arm64/apks/ChromePublic.apk
   ```

---

## 📦 GitHub Actions CI

Automated standalone APK builds can be triggered directly in GitHub Actions using `.github/workflows/build_kiwi_standalone.yml`.

---

## 📄 License
Kiwi Browser is licensed under the same license as Chromium (BSD 3-Clause).
