#!/usr/bin/bash
rm -rf ./target
javac -encoding utf-8 -d ./target ./ieee/*.java -cp "lib/ieee754lib-1.0.1.jar;resources/icon.png"
cp -r lib ./target/lib
mkdir ./target/resources
cp resources/icon.png ./target/resources/icon.png
cp resources/Manifest.txt target/Manifest.txt
pushd ./target
jar cfm ./application.jar ./Manifest.txt ./ieee/* ./resources/*
popd
launch4jc ./resources/execonfig.xml
rm -f ./IEEE754Converter.exe
mv ./target/IEEE754Converter.exe .
# rm -rf ./target