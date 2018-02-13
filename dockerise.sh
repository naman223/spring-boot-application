#!/usr/bin/env bash
set -e
docker login
docker build -t namanmehta/zipkin-service-one:1.0 .

