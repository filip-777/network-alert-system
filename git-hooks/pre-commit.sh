#!/bin/bash

echo "Run tests with mvnw..."

if [ ! -f mvnw ]; then
    echo "Error: Maven Wrapper (mvnw) not found."
    echo "Check if mvnw is configured."
    exit 1
fi

./mvnw clean test

if [ $? -ne 0 ]; then
    echo "Error: Tests did not passed."
    exit 1
else
    echo "Tests completed with success."
fi

exit 0