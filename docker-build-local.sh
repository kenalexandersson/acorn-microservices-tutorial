#!/bin/bash

mvn compile com.google.cloud.tools:jib-maven-plugin:2.7.0:dockerBuild -f items-service
mvn compile com.google.cloud.tools:jib-maven-plugin:2.7.0:dockerBuild -f reviews-service
mvn compile com.google.cloud.tools:jib-maven-plugin:2.7.0:dockerBuild -f webapi
