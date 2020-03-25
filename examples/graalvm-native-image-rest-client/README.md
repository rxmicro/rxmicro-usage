https://github.com/oracle/graal/blob/master/substratevm/LIMITATIONS.md


# Using agent

```shell script
java -jar target/ProxyMicroService.jar

$GRAALVM_HOME/bin/java -agentlib:native-image-agent=config-output-dir=.graal -jar target/ProxyMicroService.jar
```

```shell script
2020-03-24 21:04:44.902 [INFO] global : Using java.util.logging with default config
Exception in thread "main" java.lang.InternalError: java.security.NoSuchAlgorithmException: class configured for SSLContext (provider: SunJSSE) cannot be found.
        at jdk.internal.net.http.HttpClientImpl.<init>(HttpClientImpl.java:268)
        at jdk.internal.net.http.HttpClientImpl.create(HttpClientImpl.java:253)
        at jdk.internal.net.http.HttpClientBuilderImpl.build(HttpClientBuilderImpl.java:135)
        at io.rxmicro.http.client.jdk.internal.JdkHttpClient.<init>(JdkHttpClient.java:97)
        at io.rxmicro.http.client.jdk.internal.JdkHttpClientFactory.create(JdkHttpClientFactory.java:35)
        at io.rxmicro.rest.client.internal.RestClientBuilder.createHttpClient(RestClientBuilder.java:41)
        at io.rxmicro.rest.client.detail.RestClientImplFactory.createRestClient(RestClientImplFactory.java:48)
        at rxmicro.$$RestClientFactoryImpl.lambda$new$0($$RestClientFactoryImpl.java:26)
        at io.rxmicro.runtime.local.provider.LazyInstanceProvider.getInstance(LazyInstanceProvider.java:52)
        at java.util.Optional.map(Optional.java:265)
        at io.rxmicro.runtime.local.InstanceContainer.getSingleton(InstanceContainer.java:117)
        at io.rxmicro.runtime.local.AbstractFactory.getImpl(AbstractFactory.java:82)
        at io.rxmicro.rest.client.RestClientFactory.getRestClient(RestClientFactory.java:36)
        at io.rxmicro.examples.graalvm.nativeimage.rest.client.ProxyMicroService.<init>(ProxyMicroService.java:31)
        at io.rxmicro.examples.graalvm.nativeimage.rest.client.$$ProxyMicroService.postConstruct($$ProxyMicroService.java:29)
        at io.rxmicro.rest.server.internal.Router.injectDependencies(Router.java:118)
        at io.rxmicro.rest.server.internal.Router.register(Router.java:109)
        at io.rxmicro.examples.graalvm.nativeimage.rest.client.$$ProxyMicroService.register($$ProxyMicroService.java:40)
        at io.rxmicro.rest.server.detail.component.RestControllerAggregator.lambda$register$1(RestControllerAggregator.java:49)
        at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:177)
        at java.util.AbstractList$RandomAccessSpliterator.forEachRemaining(AbstractList.java:720)
        at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
        at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
        at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
        at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
        at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
        at io.rxmicro.rest.server.detail.component.RestControllerAggregator.register(RestControllerAggregator.java:48)
        at io.rxmicro.rest.server.local.component.RestServerLauncher.launch0(RestServerLauncher.java:65)
        at io.rxmicro.rest.server.local.component.RestServerLauncher.launchWithFilter(RestServerLauncher.java:56)
        at io.rxmicro.rest.server.RxMicro.start(RxMicro.java:101)
        at io.rxmicro.rest.server.RxMicro.startRestServer(RxMicro.java:66)
        at io.rxmicro.examples.graalvm.nativeimage.rest.client.ProxyMicroService.main(ProxyMicroService.java:39)
Caused by: java.security.NoSuchAlgorithmException: class configured for SSLContext (provider: SunJSSE) cannot be found.
        at java.security.Provider$Service.getImplClass(Provider.java:1863)
        at java.security.Provider$Service.newInstance(Provider.java:1824)
        at sun.security.jca.GetInstance.getInstance(GetInstance.java:236)
        at sun.security.jca.GetInstance.getInstance(GetInstance.java:164)
        at javax.net.ssl.SSLContext.getInstance(SSLContext.java:168)
        at javax.net.ssl.SSLContext.getDefault(SSLContext.java:99)
        at jdk.internal.net.http.HttpClientImpl.<init>(HttpClientImpl.java:266)
        ... 33 more
Caused by: java.lang.ClassNotFoundException: sun.security.ssl.SSLContextImpl$DefaultSSLContext
        at com.oracle.svm.core.hub.ClassForNameSupport.forName(ClassForNameSupport.java:60)
        at java.lang.Class.forName(DynamicHub.java:1211)
        at java.security.Provider$Service.getImplClass(Provider.java:1848)
        ... 39 more
nedis@dev:/home/rxmicro/workspace/rxmicro-usage/examples/graalvm-native-image-rest-client$ 
```