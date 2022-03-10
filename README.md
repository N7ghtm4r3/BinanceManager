# BinanceManager
**v1.0.0**

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
	implementation 'com.github.N7ghtm4r3:BinanceManager:1.0.0'
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
    <version>1.0.0</version>
</dependency>
```

## Endpoints available

- Wallet **(Signed manager)**
- Market Data **(NO-Signed manager)**
- Spot Account/Trade **(Signed manager)**
- Margin Account/Trade **(Signed manager)**

The other endpoints will be gradually released


## Usage/Examples

### No-Signed Managers

```java

// with automatic research for a workly basepoint
try {
    BinanceMarketManager binanceMarketManager = new BinanceMarketManager();
} catch (SystemException | IOException e) {
    e.printStackTrace();
}

// choose basepoint manually (index from 0 to 3)
try {
    BinanceMarketManager binanceMarketManager = new BinanceMarketManager(BinanceManager.BASE_ENDPOINTS.get(0));
} catch (SystemException | IOException e) {
    e.printStackTrace();
}
```

### Signed Managers (requests with apiKey)

```java

// with automatic research for a workly basepoint
try {
    BinanceSpotManager binanceSpotManager = new BinanceSpotManager("yourApiKey","yourSecretKey");
} catch (SystemException | IOException e) {
    e.printStackTrace();
}

// choose basepoint manually (index from 0 to 3)
try {
    BinanceSpotManager binanceSpotManager = new BinanceSpotManager(BinanceManager.BASE_ENDPOINTS.get(0),"yourApiKey","yourSecretKey");
} catch (SystemException | IOException e) {
    e.printStackTrace();
}
```

### Responses

In this example manager is NO signed type manager, but is same for signed type managers

- String: will return response formatted as String object

```java
try {
    System.out.println(binanceMarketManager.getPriceTicker("BTCBUSD"));
} catch (IOException e) {
    e.printStackTrace();
}
```

- JSON: will return response formatted as JSON (JSONObject or JSONArray)

```java
try {
    System.out.println(binanceMarketManager.getJSONPriceTicker("BTCBUSD"));
} catch (IOException e) {
    e.printStackTrace();
}
```

- Custom-object: will return response formatted as custom object provided by library

```java
try {
    System.out.println(binanceMarketManager.getObjectPriceTicker("BTCBUSD"));
} catch (IOException e) {
    e.printStackTrace();
}
```

- Primitives: some requests will return primitive types like boolean, long, double

```java
// it return long type es. 151596910325
try {
    System.out.println(binanceMarketManager.getTimestamp());
} catch (SystemException | IOException e) {
    e.printStackTrace();
}
```

### Errors handling

```java
try {
    System.out.println(binanceMarketManager.getTimestamp());
} catch (Exception e) {
    System.out.println(binanceMarketManager.getErrorResponse());
}
/* NOTE: if is not a request error will appear: "No HTTP Binance request error, check your code"
  and you will have to work on your code to manage error*/
```
## 🛠 Skills
- Java

## Authors

- [@N7ghtm4r3](https://www.github.com/N7ghtm4r3)

## Support

For support, email infotecknobitcompany@gmail.com.

## Badges

[![](https://img.shields.io/badge/Google_Play-414141?style=for-the-badge&logo=google-play&logoColor=white)](https://play.google.com/store/apps/developer?id=Tecknobit)
[![](https://img.shields.io/badge/Binance-FCD535?style=for-the-badge&logo=binance&logoColor=white)](https://www.binance.com/)
[![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://github.com/N7ghtm4r3/BinanceManager/blob/main/README.md)

[![Twitter](https://img.shields.io/twitter/url/https/twitter.com/cloudposse.svg?style=social&label=Tecknobit)](https://twitter.com/tecknobit)
[![](https://jitpack.io/v/N7ghtm4r3/BinanceManager.svg)](https://jitpack.io/#N7ghtm4r3/BinanceManager)

## Donations 

If you want support project and developer: **0x5f63cc6d13b16dcf39cd8083f21d50151efea60e**

![](https://img.shields.io/badge/Bitcoin-000000?style=for-the-badge&logo=bitcoin&logoColor=white) 
![](https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white)


Copyright © 2022 Tecknobit
