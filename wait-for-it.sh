#!/bin/bash
# wait-for-it.sh: waits for a service to be available before proceeding

# Usage: wait-for-it.sh host:port -- command arguments
# Example: wait-for-it.sh postgres:5432 -- java -jar app.jar

host="$1"
shift
port="$1"
shift

# Wait for the host and port to be available
until nc -z "$host" "$port"; do
  echo "Waiting for $host:$port..."
  sleep 1
done

echo "$host:$port is available!"

# Execute the passed command
exec "$@"
