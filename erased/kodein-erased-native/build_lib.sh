#!/bin/bash

rm -rf lib/ kodein-erased.klib

unzip build/konan/libs/linux/kodein-erased.klib -x linkdata/module_data_flow_graph "targets/*" -d lib/

for target in $(ls build/konan/libs/)
do
    unzip build/konan/libs/$target/kodein-erased.klib "targets/*" -d lib/
done

(cd lib/ && zip -r ../kodein-erased.klib *)
