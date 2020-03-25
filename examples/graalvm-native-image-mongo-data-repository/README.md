# Start DB

```shell script
docker run -it --rm -p 27017:27017 -e MONGO_INITDB_DATABASE=db mongo:4.2.3-bionic
```