# Build:
# docker build -t rxmicro/postgres-test-db .
#
# Run:
# docker run -it --rm --name rxmicro-postgres-test-db -p 5432:5432 rxmicro/postgres-test-db
#
# Push:
# docker push rxmicro/postgres-test-db
#
# Documentation links
# Docker:           https://docs.docker.com/engine/reference/run/
# With-Test-Data:   https://nickjanetakis.com/blog/docker-tip-79-saving-a-postgres-database-in-a-docker-image
# ----------------------------------------------------------------------------------------------------------------
FROM postgres:9.6.17-alpine

COPY ./src/main/sql/01.ddl.sql    /docker-entrypoint-initdb.d/01.ddl.sql
COPY ./src/main/sql/02.data.sql   /docker-entrypoint-initdb.d/02.data.sql

ENV POSTGRES_DB=rxmicro
ENV POSTGRES_USER=rxmicro
ENV POSTGRES_PASSWORD=password
ENV PGDATA=/data