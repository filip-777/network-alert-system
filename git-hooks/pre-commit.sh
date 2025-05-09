#!/bin/bash

config_local_folder="./config/local"
config_is_set_up="false"

if [ ! -d "$config_local_folder" ]; then
  ls  ./config/local
  pwd
  echo "There is no local config definition at path '$config_local_folder' ."
  exit 1
else
  source ${config_local_folder}/java_local_config.sh
  echo "Local config set up"
  config_is_set_up="true"
fi

echo "DEBUG: config_is_set_up is '$config_is_set_up'"

if [ "$config_is_set_up" == "true" ]; then

  echo "Run tests with mvnw..."

  if [ ! -f mvnw ]; then
      echo "Error: Maven Wrapper (mvnw) not found."
      echo "Check if mvnw is configured."
      exit 1
  fi

  ./mvnw prettier:check
  ./mvnw clean verify

  if [ $? -ne 0 ]; then
      echo "Error: Tests did not passed."
      exit 1
  else
      echo "Tests completed with success."
  fi
fi

exit 0