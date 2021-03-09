
# Altimetrik recruitment assignment

Payment CRUD application using hexagonal architecture.

### Running
`mvn spring-boot:run`

application by default starts on port 8080

### Configuration

Currently this app allows only CSV file storage (set by default)
other persistence methods can be easly implemented

`datasource.implementation` property is used to define the persistance method

default is `csv`. `h2` is also possible,
app will not start because the implementation is empty. Possibility to switch created as a POC

### Usage

App exposes a REST api under `/api/payment` endpoint

* Fetch all payments : `GET /api/payment/`
* Create payment : `POST /api/payment/`
* Fetch payment by payment UUID : `GET /api/payment/:UUID`
* Update payment : `PUT /api/payment/:UUID`
* Delete payment : `DELETE /api/payment/:UUID`

#### POST/PUT body json schema

```json
{
  "type": "object",
  "properties": {
    "amount": {
      "type": "number"
    },
    "currencyCode": {
      "type": "string"
    },
    "userId": {
      "type": "integer"
    },
    "targetAccountNumber": {
      "type": "string"
    }
  },
  "required": [
    "amount",
    "currencyCode",
    "userId",
    "targetAccountNumber"
  ]
}
```

**example**
```json
{
    "amount": 500.5,
    "currencyCode": "PLN",
    "userId": 123,
    "targetAccountNumber": "acc123123"
}
```

### Running tests

* Unit tests - `mvn clean test`
* Integration and unit tests `mvn clean integration-test`

