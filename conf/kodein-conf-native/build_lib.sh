#!/bin/bash

rm -rf lib/ kodein-conf.klib

unzip build/konan/libs/linux/kodein-conf.klib -x linkdata/module_data_flow_graph "targets/*" -d lib/

for target in $(ls build/konan/libs/)
do
    unzip build/konan/libs/$target/kodein-conf.klib "targets/*" -d lib/
done

(cd lib/ && zip -r ../kodein-conf.klib *)
