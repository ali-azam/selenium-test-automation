#!/bin/bash

echo $@

${jvmargs[@]}

DIR=$(dirname $0)

args=( "$@" )
javaProps=( )
mainclass=com.automation.Main
server_jvmargs=( -Djava.awt.headless=true -Xms1024m -Xmx1024m "${jvmargs[@]}" )
XX_HOME="$DIR"
client_classpath="$XX_HOME/lib/others/*:$XX_HOME/lib/selenium-java-3.3.1/*:$XX_HOME/lib/selenium-java-3.3.1/lib/*:$XX_HOME/lib/TestNG/*:$XX_HOME/lib/mailactivation/*:$XX_HOME/lib/extentreports-java-3.0.3/*:$XX_HOME/lib/extentreports-java-3.0.3/lib/*:$XX_HOME/bin"

BIN_PATH="$XX_HOME/bin"
SRC_PATH="$XX_HOME/src"

rm -rfv "$BIN_PATH"
mkdir -p "$BIN_PATH"

cd $DIR

javac \
	-cp "$client_classpath" \
	-d "$BIN_PATH" \
	-sourcepath $SRC_PATH src/com/automation/*.java
	
javac \
	-cp "$client_classpath" \
	-d "$BIN_PATH" \
	-sourcepath $SRC_PATH src/com/automation/**/*.java
	
java \
  "${server_jvmargs[@]}" \
  "${javaProps[@]}" \
  -Dxx.home="$XX_HOME" \
  -Duser.dir="$XX_HOME" \
  -cp "$client_classpath" \
  "$mainclass" $DIR $1