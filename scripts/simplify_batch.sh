#!/bin/sh -x

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

for src in "$@"
do
  dir=$(dirname "$src")
  filename=$(basename -- "$src")
  filename="${filename%.*}"
  txt_file=$dir/$filename.txt
  json_file=$dir/$filename.json
  simp_file=$dir/$filename"_simp.txt"
  echo $src $txt_file $json_file $simp_file
  python scripts/docx2text.py "$src" "$txt_file"
  $JAVACMD -Xmx1024m -classpath "$CLASSPATH" main.Console -json "$txt_file" "$json_file"
  python scripts/generate_sentences.py "$json_file" "$simp_file"
done

#set -- "$@"
#$JAVACMD -Xmx1024m -classpath "$CLASSPATH" main.Console -json $1 $2
#python scripts/generate_sentences.py $2 $3
