#!/bin/sh -x

#!/bin/sh

# Might fail if $0 is a link
TAGGER_HOME=`dirname "$0"`
CLASSPATH="$TAGGER_HOME/lib/*:$TAGGER_HOME/bin"
OUTPUT_DIR="$TAGGER_HOME/bin"
SRC_DIR="$TAGGER_HOME/src"

# get java
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    JAVACMD="$JAVA_HOME/bin/java"
  else
    JAVACMD="`which java`"
  fi
fi

$JAVACMD -Xmx1024m -classpath $CLASSPATH:$OUTPUT_DIR main.Console $@

