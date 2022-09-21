#!/bin/bash
cd backend
mvn clean compile package
java -jar .\OauthAuthorizationServer\target\OauthAuthorizationServer-1.0-SNAPSHOT.jar
java -jar .\timemanager\target\timemanager-1.0-SNAPSHOT.jar