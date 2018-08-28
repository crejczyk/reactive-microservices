[![Build Status](https://travis-ci.org/crejczyk/reactive-microservices.svg?branch=master)](https://travis-ci.org/crejczyk/reactive-microservices)
[![GitHub license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/crejczyk/reactive-microservices/blob/master/LICENSE)

# reactive-microservices-rest-api


## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Redis

5. Docker


## Exploring the Rest API

The Expert application defines following REST APIs

```
POST /experts - Create a new Expert

GET /experts/{expertId}/status - Retrieve a Expert status by Id

PUT /experts/{expertId}/status - Update a Expert status by Id

PUT /experts/{expertId}/location - Update a Expert location

GET /experts - Retrieve a Experts by parameters
```

The ExpertBooking application defines following REST APIs

```
PUT /expertbookings/accept - Accept the Booking

PUT /expertbookings/cancel - Cancel the Booking

GET /expertbookings - Retrieve all bookings by parameters

POST /expertbookings - Create a new Booking
```


