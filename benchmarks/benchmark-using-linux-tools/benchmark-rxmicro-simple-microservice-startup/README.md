# time

## Using JVM

```shell script
time java -jar target/HelloWorldMicroService.jar

real    0m0,972s
user    0m1,105s
sys     0m0,117s
```
--------------------------------------------------------------------------------------------------------------------------------------------
## Using Native Image

```shell script
time ./HelloWorldMicroService

real    0m0,012s
user    0m0,001s
sys     0m0,012s
```

# perf

## Using JVM

```shell script
sudo perf stat -e cpu-clock -r20 java -jar target/HelloWorldMicroService.jar

Performance counter stats for 'java -jar target/HelloWorldMicroService.jar' (20 runs):

  1 316,08 msec cpu-clock                 #    1,257 CPUs utilized            ( +-  1,13% )
  1,04681 +- 0,00802 seconds time elapsed  ( +-  0,77% )  

```
--------------------------------------------------------------------------------------------------------------------------------------------
## Using Native Image

```shell script
sudo perf stat -e cpu-clock -r20 ./HelloWorldMicroService

Performance counter stats for './HelloWorldMicroService' (20 runs):
  10,26 msec cpu-clock                 #    0,974 CPUs utilized            ( +-  5,58% )
  0,010530 +- 0,000609 seconds time elapsed  ( +-  5,78% )
```