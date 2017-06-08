#!/bin/bash

rm -rf out

classpath=$( cd .. && ./gradlew -q classpath )

java \
    -jar dokka-fatjar.jar \
    -format gfm \
    -classpath $classpath \
    -include ../kodein-core/dokka-module.md:../kodein-core/dokka-package.md:../kodein-android/dokka-package.md:../kodein-conf/dokka-package.md:../kodein-erased/dokka-package.md:../kodein-jxinject/dokka-package.md \
    ../kodein/src/main/kotlin                                \
    ../kodein-android/src/main/java                          \
                                    ../shared/conf/kotlin    \
    ../kodein-core/src/main/kotlin  ../shared/core/kotlin    \
                                    ../shared/erased/kotlin  \
    ../kodein-jxinject/src/main/kotlin
