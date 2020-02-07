#! /bin/bash -x

# Might fail if $0 is a link
CURRENT_DIR=$(dirname "$0")
WORKING_DIR="$(dirname "$CURRENT_DIR")"
CLASSPATH="$WORKING_DIR/lib/*:$WORKING_DIR/bin"
OUTPUT_DIR="$WORKING_DIR/bin"
SRC_DIR="$WORKING_DIR/src"

# get javac
if [ -z "$JAVACCMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    JAVACCMD="$JAVA_HOME/bin/javac"
  else
    JAVACCMD="$(command -v javac)"
  fi
fi

# create bin folder
if [ ! -d "$OUTPUT_DIR" ]; then
  mkdir "$OUTPUT_DIR"
fi

# clean bin folder
find "$OUTPUT_DIR" -type f -name '*.class' -exec rm -f {} \;

# compile
find "$SRC_DIR" -name '*.java' > sources_list.txt
$JAVACCMD -g -d "$OUTPUT_DIR" -classpath "$CLASSPATH" @sources_list.txt
rm -f sources_list.txt
