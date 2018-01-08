#!/bin/bash

rm -rf lib/ kodein-core.klib

unzip build/konan/libs/linux/kodein-core.klib -x linkdata/module_data_flow_graph "targets/*" -d lib/

for target in $(ls build/konan/libs/)
do
    unzip build/konan/libs/$target/kodein-core.klib "targets/*" -d lib/
done

(cd lib/ && zip -r ../kodein-core.klib *)
