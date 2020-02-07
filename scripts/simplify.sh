#!/bin/sh -x

#!/bin/sh

# Might fail if $0 is a link
CURRENT_DIR=$(dirname "$0")
WORKING_DIR="$(dirname "$CURRENT_DIR")"
CLASSPATH="$WORKING_DIR/lib/*:$WORKING_DIR/bin"

# get java
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    JAVACMD="$JAVA_HOME/bin/java"
  else
    JAVACMD="$(command -v java)"
  fi
fi

set -- "$@"
$JAVACMD -Xmx1024m -classpath "$CLASSPATH" main.Console -json $1 $2
python scripts/generate_sentences.py $2 $3
