# Reservation Code Challenge


ParkHere code assignment
LICENSE

Copyright (C) ParkHere GmbH - All Rights Reserved
Unauthorized copying of any content of this project via any medium is strictly prohibited, Proprietary and confidential.

Introduction
=============================
Hello and welcome!

As part of our interview process, we would like you to complete a programming assignment. It presents a hypothetical situation which resembles how you could be solving problems at ParkHere. 

Please read the whole description thoroughly then create a program to solve the problem at the hand.
For the solution, we request that you use Java 11+ or Kotlin as a programming language. The application must run and your solution should provide sufficient evidence that it is complete.

###Note1: 
Using any application frameworks such as Spring Boot or DropWizard is totally optional and up to you however developing the solution with AWS Serverless solution will be an extra advantage for you.

###Note2: 
You are free to use any database, middleware (messaging platform, etc.) of your choice


##Parking Reservation System

The company decided to develop a product which allows users to reserve parking spots of a car park through our mobile app before they get there. Therefore, when they arrive, they would know exactly where to park. 

As we are using `Microservices`, we would like to have the solution to be built based on loosely coupled services and general attributes of distributed systems.
We would like to run our business in many countries therefore, we would like to have the system to be scalable according to the demand. 

We’re also concerned about the performance, and therefore we aim for `500ms` response time and also the availability of the system is important as our customers’ satisfaction is something very much valuable to us.

##Part 1: System Design

We would like you to use a drawing tool such as (https://www.draw.io) to illustrate your design in terms of microservices, their interaction styles, preferred databases and any other components you might have specified for your solution.

##Part2: Parking Reservation (coding)

Now, we would like you to implement below endpoint from reservation-microservice:

`url: POST 	/api/reservations`

```
ReservationRequest: {
    "user-id": "john@park-here.eu",
    "car-plate": "K-SC-124"
    "start-timespan": "00: 00: 01",
    "end-timespan": "12:00: 00",
    "reserve-date": "02-23-2022"
}

ReservationResponse: {
    "Status": 200,
    "reserved-spot": "P12",
    "reservation-id": 123
}
```

- The parking lot has 500 parking spots
- Users should be able to reserve for an arbitrary timespan. 
- Our system should decide which parking spot they get
- Multiple reservations for one user are possible
- No two users can reserve the same spot for specific timespan
- The reservation can be made for either half-day (morning or evening) or full-day

###Valid assumptions:

- The user will be charged for his reservations based on a tariff defined within `tariff-microservice` 
- the invoice is generated at the end of each month through `invoice-microservice` but the implementation of these services is not part of this assignment
- The system uses different payment options implemented within `payment-microservice` but the implementation is not part of this assignment
- The information about parking lot such as its capacity, location, number of floors, sectors and etc. are all defined within `configuration-microservice` but the implementation is not part of this assignment
- For simplicity, all parking spots would be considered to be the same for all sort of cars
- No security mechanism (`authentication` and `authorization`) would be needed for this code assignment
- Entities can be created with minimum required properties
- Analytical reports are provided to the managers by `analytics-microservice` but the implementation is not part of this assignment

##Part3: Roll out events (Design only)

The business has gone extremely wide, and now we want to roll out our app to 5000 new users. There is one condition: The operator of parking slot wants us every Thursday at 6pm to “unlock” the next week. This will lead to a huge traffic spike on Thursday evening since users want to make reservations for the next week. 

Our assumption is that every user will create 3 reservations. So, we expect more than 15000 requests for reservations within a couple of seconds. The operation of creating a reservation currently takes 500ms to be completed.

How would you scale your solution to fulfill this requirement? 
#### No coding is required for this part, but you may use the drawing tool to elaborate your approach

## Tips ##

* We value writing clean codes. Your code will be reviewed by our developers, so make sure it is easy to follow and well-structured.
* Don't feel the need to over-engineer your solution. We don't expect you to build an entire system that can scale to billions of events. Your solution should be tailored to the problem statement.

##How to submit the solution

As of now, you have given access to the private repository for this challenge. All you have to do is to clone this repository and create a branch with a meaningful name which will include your name as well.  Once you’re done with the implementation, kindly create a merge request and add our colleague who is currently in contact with you as the reviewer so he can review it and get back to you.

Please make sure you include a high quality image of your drawing within the solution or provide a link if you’re using an online tool.


Good luck!  
ParkHere Dev Team

