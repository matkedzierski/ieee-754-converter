#!/usr/bin/bash
rm -rf ./target
javac -encoding utf-8 -d ./target ./ieee/*.java -cp lib/ieee754lib-1.0.1.jar
cp -r lib ./target/lib
cp resources/Manifest.txt target/Manifest.txt
pushd ./target
jar cfm ./application.jar ./Manifest.txt ./ieee/*
popd
launch4jc ./resources/execonfig.xml
rm -f ./IEEE754Converter.exe
mv ./target/IEEE754Converter.exe .
rm -rf ./target