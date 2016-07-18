#!/bin/bash

echo "### Waiting for ${API_HOST}:${API_PORT} to get ready..."
while ! nc -vz ${API_HOST} ${API_PORT}
do
  echo "### Retry..."
  sleep 3;
done

echo "### Starting unit tests."
nodeunit lambdatests.js
