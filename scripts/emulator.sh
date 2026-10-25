#!/bin/zsh
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
# shellcheck disable=SC1091
source "$ROOT/scripts/env.sh"

AVD_NAME="Diary"
if ! emulator -list-avds | grep -qx "$AVD_NAME"; then
  echo "Creating $AVD_NAME emulator (Pixel 6, Android 14)…"
  printf 'no\n' | avdmanager create avd \
    --name "$AVD_NAME" \
    --package "system-images;android-34-ext8;google_apis_playstore;arm64-v8a" \
    --device pixel_6 \
    --force
fi

echo "Starting $AVD_NAME. A phone window should open on your Mac."
exec emulator -avd "$AVD_NAME" -netdelay none -netspeed full
