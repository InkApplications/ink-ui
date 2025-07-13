#!/usr/bin/env bash

VERSION={{VERSION}}
URL=https://github.com/InkApplications/ink-ui/releases/download/${VERSION}/inkui-${VERSION}.zip
SHA256={{HASH}}
INSTALL_DIR="$HOME/.local/share/inkui"
INKUI_BIN="$HOME/.local/share/inkui/inkui-${VERSION}/bin/inkui"

if [ ! -f "$INKUI_BIN" ]; then
    echo "Downloading inkui..."
    TEMP_FOLDER=$(mktemp -d)
    ZIP_FILE="${TEMP_FOLDER}/inkui.zip"
    curl -L "$URL" -o "$ZIP_FILE"
    echo "$SHA256 $ZIP_FILE" | sha256sum -c -
    if [ $? -ne 0 ]; then
        echo "Checksum verification failed!"
        exit 1
    fi
    mkdir -p "${INSTALL_DIR}/${SHA256}"
    unzip $ZIP_FILE -d "${INSTALL_DIR}/${SHA256}"
fi

"$INKUI_BIN" $@
