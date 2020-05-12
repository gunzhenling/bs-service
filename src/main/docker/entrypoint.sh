#!/bin/sh

echo "The application will start..."
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "/app.jar" "$@"
