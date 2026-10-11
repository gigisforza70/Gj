#!/usr/bin/env bash
# ==============================================================================
# Kiwi Browser Revived - Local Build Script
# ==============================================================================
set -e

ARCH="${1:-arm64}"
BUILD_DIR="out/android_${ARCH}"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "=== Kiwi Browser Revived Builder ==="
echo "Target Architecture: ${ARCH}"
echo "Build Output: ${BUILD_DIR}"

# 1. Check for depot_tools
if ! command -v gn &> /dev/null || ! command -v ninja &> /dev/null; then
    if [ ! -d "$HOME/depot_tools" ]; then
        echo "--> Downloading Google depot_tools..."
        git clone https://chromium.googlesource.com/chromium/tools/depot_tools.git "$HOME/depot_tools"
    fi
    export PATH="$HOME/depot_tools:$PATH"
fi

# 2. Check Java
if ! command -v keytool &> /dev/null; then
    echo "ERROR: OpenJDK not found. Please install openjdk-8-jdk or openjdk-11-jdk."
    exit 1
fi

# 3. Keystore generation
if [ ! -f "$SCRIPT_DIR/keystore.jks" ]; then
    echo "--> Generating build keystore..."
    keytool -genkey -v -keystore "$SCRIPT_DIR/keystore.jks" -alias dev \
        -keyalg RSA -keysize 2048 -validity 10000 \
        -storepass public_password -keypass public_password \
        -dname "cn=Kiwi Browser Revived, ou=Community, o=Kiwi, c=US"
fi

# 4. Prepare args.gn
mkdir -p "${BUILD_DIR}"
cat "$SCRIPT_DIR/.build/production_build_reference/args.gn" | \
    sed "s#target_cpu = \"arm64\"#target_cpu = \"${ARCH}\"#" > "${BUILD_DIR}/args.gn"

echo "--> Generated ${BUILD_DIR}/args.gn:"
cat "${BUILD_DIR}/args.gn"

# 5. GN & Compilation
echo "--> Generating build configuration with gn..."
gn gen "${BUILD_DIR}"

echo "--> Building ChromePublic.apk with ninja..."
ninja -C "${BUILD_DIR}" chrome_public_apk

echo "=== Build Complete! ==="
echo "APK located at: ${BUILD_DIR}/apks/ChromePublic.apk"
