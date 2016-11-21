
## Exchange Microservice

### Description

The Exchange Microservice provides a few end-points to query Bitcoin exchange information. 
The application persistently queries public api's for BTC to USD rates from 2 sources every 5 seconds. 
This asynchronous action saves the ticker data to a MongoDB database 'exchange' running on localhost at port 27017. 

### Prerequisites

* Git
* Java 8
* Maven 3.x
* MongoDB 3.x

### Install / Run

```shell
$ git clone https://github.com/sfskier/exchange.git
$ cd exchange
$ mvn package
$ java -jar target/exchange-0.0.1-SNAPSHOT.jar
```

### API

Returns latest BTC USD rate.  
<http://localhost:8080/rates>

Returns BTC USD rate within 5 seconds of time specified in milliseconds.  
<http://localhost:8080/rates/historical/{time}>

<https://currentmillis.com/> is helpful for obtaining a date in milliseconds.

### Response

The response is returned in the form:

```json
{
  "bid":"734.97",
  "ask":"735.62",
  "last":"734.00",
  "created":"2016-11-21T08:01:28.552+0000"
}
```
