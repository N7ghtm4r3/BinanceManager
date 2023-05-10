# BinanceManager
**v2.0.1**

This is a Java Based library useful to work with Binance's API service.

## Implementation

Add the JitPack repository to your build file

### Gradle

- Add it in your root build.gradle at the end of repositories

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
- Add the dependency

```gradle
dependencies {
    implementation 'com.github.N7ghtm4r3:BinanceManager:2.0.1'
}
```

### Maven

- Add it in your root build.gradle at the end of repositories

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
- Add the dependency

```xml
<dependency>
    <groupId>com.github.N7ghtm4r3</groupId>
    <artifactId>BinanceManager</artifactId>
    <version>2.0.1</version>
</dependency>
```

## 🛠 Skills
- Java

## Endpoints managers available

- All endpoints managers are available to be used

## Usage/Examples

### Execution

#### No-Signed Managers

```java

// with automatic research for a working base endpoint
try{
    BinanceMarketManager binanceMarketManager = new BinanceMarketManager();
}catch(Exception e){
    e.printStackTrace();
 }

// choose base endpoint manually
try{
    BinanceManager binanceManager = new BinanceManager(BinanceManager.BinanceEndpoint);
}catch(Exception e){
    e.printStackTrace();
}
```

#### Signed Managers (requests with apiKey)

```java

// with automatic research for a working base endpoint
try{
    BinanceSignedManager binanceManager = new BinanceSignedManager("yourApiKey","yourSecretKey");
}catch(Exception e){
    e.printStackTrace();
}

// choose base endpoint manually
try{
    BinanceSignedManager binanceManager = new BinanceSignedManager(BinanceManager.BinanceEndpoint,"yourApiKey","yourSecretKey");
}catch(Exception e){
    e.printStackTrace();
}
```

To avoid re-entering credentials for each manager, you can instantiate managers like this with the **ARCS**:

```java
// choose the manager (for signed and no-signed managers same procedure), for example: BinanceMarketManager, BinanceWalletManager, etc 
BinanceManager firstManager = new BinanceManager(/* params of the constructor chosen */,"apiKey","secretKey");
// and then use it 
firstManager.makeSomething();
// you don't need to insert all credentials to make manager work
BinanceManager secondManager=new BinanceManager(); // same credentials used
// and then use it
secondManager.makeSomething();
```

### Responses

Library give to you the opportunity to customize the return object after a request, the possibilities are:

- **JSON:** return response formatted as **JSON** (**org.json.JSONObject** or **org.json.JSONArray**)
- **STRING:** return response formatted as **String**
- **LIBRARY_OBJECT:** return response formatted as custom object offered by the library

```java
// choose the manager for example: BinanceMarketManager, BinanceWalletManager, etc
BinanceManager manager=new BinanceManager(/* params of the constructor chosen */);
// method to return directly a library given by library
manager.someRequest(); // in this case will be returned directly a LIBRARY_OBJECT
// method to customize the format of the return 
manager.someRequest(ReturnFormat.JSON); // in this case will be returned response in JSON format
```

### Errors handling

```java
try{
    System.out.println(binanceManager.getTimestamp());
}catch(Exception e){
    System.out.println(binanceManager.getErrorResponse());
    //or
    binanceManager.printErrorResponse();
}

/* NOTE: if is not a request error will appear: "Error is not in api request, check out your code"
  and you will have to work on your code to manage error*/
```

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

If you need help using the library or encounter any problems or bugs, please contact us via the following links:

- Support via <a href="mailto:infotecknobitcompany@gmail.com">email</a>
- Support via <a href="https://github.com/N7ghtm4r3//BinanceManager/issues/new">GitHub</a>

Thank you for your help!

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![Twitter](https://img.shields.io/badge/Twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/tecknobit)

[![](https://img.shields.io/badge/Binance-FCD535?style=for-the-badge&logo=binance&logoColor=white)](https://binance-docs.github.io/apidocs/spot/en/#general-api-information)
[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)

[![](https://jitpack.io/v/N7ghtm4r3/BinanceManager.svg)](https://jitpack.io/#N7ghtm4r3/BinanceManager)

## Donations 

If you want support project and developer with crypto: **0x5f63cc6d13b16dcf39cd8083f21d50151efea60e**

![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white) 
![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white)

If you want support project and developer with <a href="https://www.paypal.com/donate/?hosted_button_id=5QMN5UQH7LDT4">PayPal</a>

Copyright © 2023 Tecknobit
