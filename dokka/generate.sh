#!/bin/bash

rm -rf out

classpath=$( cd .. && ./gradlew -q classpath )

java \
    -jar dokka-fatjar.jar \
    -format gfm \
    -classpath $classpath \
    -include ../kodein/dokka-module.md:../kodein/dokka-package.md:../kodein-android/dokka-package.md:../kodein-conf/dokka-package.md \
    ../kodein/src/main/kotlin ../kodein-android/src/main/kotlin ../kodein-conf/src/main/kotlin
