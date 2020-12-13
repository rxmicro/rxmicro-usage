# time

## Using JVM

```shell script
time java -cp target/JavaStartup.jar io.rxmicro.benchmark.java.program.startup.WithoutAnything

real    0m0,088s
user    0m0,104s
sys     0m0,028s
--------------------------------------------------------------------------------------
time java -cp target/JavaStartup.jar io.rxmicro.benchmark.java.program.startup.WithSout

real    0m0,092s
user    0m0,122s
sys     0m0,030s
```
--------------------------------------------------------------------------------------------------------------------------------------------
## Using Native Image

```shell script
time ./WithoutAnything

real    0m0,003s
user    0m0,000s
sys     0m0,003s
------------------------
time ./WithSout

real    0m0,004s
user    0m0,001s
sys     0m0,004s
```

# perf

## Using JVM

```shell script
sudo perf stat -e cpu-clock -r20 java -cp target/JavaStartup.jar io.rxmicro.benchmark.java.program.startup.WithoutAnything

Performance counter stats for 'java -cp target/JavaStartup.jar io.rxmicro.benchmark.java.startup.WithoutAnything' (20 runs):
  147,05 msec cpu-clock                 #    1,536 CPUs utilized            ( +-  2,71% )
  0,09576 +- 0,00230 seconds time elapsed  ( +-  2,40% )
---------------------------------------------------------------------------------------------------------------------------- 
sudo perf stat -e cpu-clock -r20 java -cp target/JavaStartup.jar io.rxmicro.benchmark.java.program.startup.WithSout

Performance counter stats for 'java -cp target/JavaStartup.jar io.rxmicro.benchmark.java.startup.WithSout' (20 runs):
    151,67 msec cpu-clock                 #    1,566 CPUs utilized            ( +-  2,56% )
    0,09688 +- 0,00226 seconds time elapsed  ( +-  2,33% )
```
--------------------------------------------------------------------------------------------------------------------------------------------
## Using Native Image

```shell script
sudo perf stat -e cpu-clock -r20 ./WithoutAnything

Performance counter stats for './WithoutAnything' (20 runs):
  2,10 msec cpu-clock                 #    0,879 CPUs utilized            ( +-  7,32% )
  0,002391 +- 0,000173 seconds time elapsed  ( +-  7,24% )  
----------------------------------------------------------------------------------------
sudo perf stat -e cpu-clock -r20 ./WithSout

Performance counter stats for './WithSout' (20 runs):
  2,92 msec cpu-clock                 #    0,879 CPUs utilized            ( +-  5,45% )
  0,003320 +- 0,000177 seconds time elapsed  ( +-  5,34% )
```