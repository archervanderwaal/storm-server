#!bin/bash

find ./src -name "*.java" | xargs cat | wc -l
