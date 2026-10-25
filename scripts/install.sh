#!/bin/zsh
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
# shellcheck disable=SC1091
source "$ROOT/scripts/env.sh"
cd "$ROOT"

echo "Building Diary…"
./gradlew :app:assembleDebug

echo "Waiting for an emulator or device…"
adb wait-for-device
boot=""
for _ in {1..90}; do
  boot="$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')"
  if [[ "$boot" == "1" ]]; then
    break
  fi
  sleep 2
done
if [[ "$boot" != "1" ]]; then
  echo "Emulator did not finish booting." >&2
  exit 1
fi

adb install -r "$ROOT/app/build/outputs/apk/debug/app-debug.apk"
adb shell am start -n com.migwi.diary/.MainActivity
echo "Diary is open on the device."
