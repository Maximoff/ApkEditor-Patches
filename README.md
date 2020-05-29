# ApkEditor-Patches
Патчи для ApkEditor
#!/bin/bash

# Author: Onno Benschop, onno@itmaze.com.au

# Note: This requires enough space for both archives to be extracted in the tempdir

if [ $# -ne 2 ] ; then

  echo Usage: $(basename "$0") zip1 zip2

  exit

fi

# Make temporary directories

archive_1=$(mktemp -d)

archive_2=$(mktemp -d)

# Unzip the archives

unzip -qqd"${archive_1}" "$1"

unzip -qqd"${archive_2}" "$2"

# Compare them

colordiff -r "${archive_1}" "${archive_2}"

# Remove the temporary directories

rm -rf "${archive_1}" "${archive_2}"

[عدل]
