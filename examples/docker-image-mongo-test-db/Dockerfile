# Build:
# docker build -t rxmicro/mongo-test-db .
#
# Run:
# docker run -it --rm --name rxmicro-mongo-test-db -p 27017:27017 rxmicro/mongo-test-db
#
# Push:
# docker push rxmicro/mongo-test-db
#
# Documentation links
# Docker:  https://docs.docker.com/engine/reference/run/
# ----------------------------------------------------------------------------------------------------------------
FROM mongo:4.2.3-bionic

COPY ./src/main/js/test-db-init.js    /docker-entrypoint-initdb.d/db-init.js

ENV MONGO_INITDB_DATABASE=rxmicro