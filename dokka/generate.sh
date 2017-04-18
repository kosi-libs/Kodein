#!/bin/bash

rm -rf out

classpath=$( cd .. && ./gradlew -q classpath )

java \
    -jar dokka-fatjar.jar \
    -format gfm \
    -classpath $classpath \
    -include ../kodein/dokka-module.md:../kodein/dokka-package.md:../kodein-android/dokka-package.md:../kodein-conf/dokka-package.md:../kodein-erased/dokka-package.md:../kodein-jxinject/dokka-package.md \
    ../kodein-core/src/main/kotlin ../kodein/src/main/kotlin ../kodein-erased/src/main/kotlin ../kodein-android/src/main/java ../kodein-conf/src/main/kotlin ../kodein-jxinject/src/main/kotlin
