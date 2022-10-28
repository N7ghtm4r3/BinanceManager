# BinanceManager
**v1.0.9**

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
	implementation 'com.github.N7ghtm4r3:BinanceManager:1.0.9'
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
    <version>1.0.9</version>
</dependency>
```

## 🛠 Skills
- Java

## Endpoints managers available

- Wallet **(Signed manager)**
- Market Data **(NO-Signed manager)**
- Spot Account/Trade **(Signed manager)**
- Margin Account/Trade **(Signed manager)**

The other endpoints managers will be gradually released


## Usage/Examples

### No-Signed Managers

```java

// with automatic research for a workly basepoint
try {
    BinanceMarketManager binanceMarketManager = new BinanceMarketManager();
} catch (Exception e) {
    e.printStackTrace();
}

// choose basepoint manually (index from 0 to 3)
try {
    BinanceMarketManager binanceMarketManager = new BinanceMarketManager(BinanceManager.BASE_ENDPOINTS.get(0));
} catch (Exception e) {
    e.printStackTrace();
}
```

### Signed Managers (requests with apiKey)

```java

// with automatic research for a workly basepoint
try {
    BinanceSpotManager binanceSpotManager = new BinanceSpotManager("yourApiKey", "yourSecretKey");
} catch (Exception e) {
    e.printStackTrace();
}

// choose basepoint manually (index from 0 to 3)
try {
    BinanceSpotManager binanceSpotManager = new BinanceSpotManager(BinanceManager.BASE_ENDPOINTS.get(0), "yourApiKey", "yourSecretKey");
} catch (Exception e) {
    e.printStackTrace();
}
```

### Responses

In this example manager is NO signed type manager, but is the same for signed type managers

- String: will return response formatted as {@link String} object

```java
try {
    System.out.println(binanceMarketManager.getPriceTicker("BTCBUSD"));
} catch (Exception e) {
    e.printStackTrace();
}
```

- JSON: will return response formatted as JSON (JSONObject or JSONArray)

```java
try {
    System.out.println(binanceMarketManager.getJSONPriceTicker("BTCBUSD"));
} catch (Exception e) {
    e.printStackTrace();
}
```

- Custom-object: will return response formatted as custom object provided by library

```java
try {
    System.out.println(binanceMarketManager.getObjectPriceTicker("BTCBUSD"));
} catch (Exception e) {
    e.printStackTrace();
}
```

- Primitives: some requests will return primitive types like boolean, long, double

```java
// it return long type es. 151596910325
try {
    System.out.println(binanceMarketManager.getTimestamp());
} catch (Exception e) {
    e.printStackTrace();
}
```

### Errors handling

```java
try {
    System.out.println(binanceMarketManager.getTimestamp());
} catch (Exception e) {
    System.out.println(binanceMarketManager.getErrorResponse());
    //or
    binanceMarketManager.printErrorResponse(); 
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

Copyright © 2022 Tecknobit
