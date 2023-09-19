#!/bin/sh
# ------------------------------------------------------
# IntelliJ IDEA offline inspection script.
# ------------------------------------------------------

export DEFAULT_PROJECT_PATH="$(pwd)"

IDE_BIN_HOME="${0%/*}"
exec "$IDE_BIN_HOME/../MacOS/idea" inspect "$@"
