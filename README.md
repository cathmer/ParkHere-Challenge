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

### Note 1: 
Feel free to use any application frameworks such as Spring Boot or DropWizard for your implementation, however developing the solution with AWS lambda functions will be considered as an extra advantage for you.

### Note 2: 
You are also free to use any database, middleware (messaging platform, etc.) of your choice which suits best for your application according to the given requirements.


##Parking Reservation System

The company decided to develop a product which allows users to reserve parking spots of a car park through our mobile app before they get there. Therefore, when they arrive they would know exactly where to park. As we are using Microservices, we would like to have the solution to be built based on loosely coupled microservices which follows the general principles of highly demanded distributed systems such as scalability, availability and reliability.

We would like to run our business in many countries therefore, we would like to have the system to be scalable according to our customer demands. We’re also concerned about the performance and therefore we aim for 500ms response time from our reservation system.

## Part 1: Parking Reservation (coding)

You have available configuration location that gives you a list of parking spots in a given parking lot. 

The service endpoint is `GET /api/parking-lots/{parking-lot-id}` and will return a list of available parking spots for a given location as below:

```
[
    {
        id: 1,
        spotName: "spot1",
        priority: 1
    },
    ...
]
```

You can find a json file attached to use as example.

Now, we would like you to implement below endpoint to create reservations in the given parking lot based on the list above.


`POST 	/api/parking-lots/{parking-lot-id}/reservations`

```
ReservationRequest: {
    "user-id": "john@park-here.eu",
    "startTimestamp": 1737586800000,
    "endTimestamp": 1737627502000
}

ReservationResponse: {
    "id": 123,
    "spotId": 1,
    "startTimestamp": 1737586800000,
    "endTimestamp": 1737627502000,
}
```

The reservation service must meet the following requirements:

- Reservation should be stored permanently.
- Users should be able to reserve for any time frame with given `startTimestamp` less than `endTimestamp`. 
- When making reservation user should be given the spot with highest priority or be informed if no spots are available
- Multiple reservations for one user are possible unless the time frames overlap
- No two users can reserve the same spot for specific timespan 

### Valid assumptions:

Below microservices are provided to you as an option, so you may use any of them within your solution if you feel you need them without thinking about their implementations.
However, you would need to specify the operations’ signature for that particular microservice if you decided to use it in your solution without implementing those operations so we would know what would you expect from that microservice in order serve your purpose.


- The information about parking lot such as its capacity, location and etc. are all defined within `configuration-microservice` 
- No security mechanism (`authentication` and `authorization`) would be needed for this code assignment
- Entities can be created with minimum required properties

## Part 2: System Design

We would like you to use a drawing tool such as (https://www.draw.io) to illustrate your design for the major components of such a system in terms of microservices, their interaction styles, preferred databases and any other components you might need for your solution. 

## Part 3: Deployment (Optional - Bonus)

As a bonus part, we would like you to describe a deployment plan for you solution in a cloud provider of your choice.

AWS is preferred, but other providers are acceptable.

In ParkHere we use Terraform. 


## Tips ##

* We value writing clean codes. Your code will be reviewed by our developers, so make sure it is easy to follow and well-structured.

* Don't feel the need to over-engineer your solution. We don't expect you to build an entire system that can scale to billions of events. Your solution should be tailored to the problem statement.

## How to submit the solution

As of now, you have given access to the private repository for this challenge. All you have to do is to clone this repository and create a branch with a meaningful name which will include your name as well.  Once you’re done with the implementation, kindly create a merge request and add our colleague who is currently in contact with you as the reviewer so he can review it and get back to you.

Please make sure you include a high quality image of your drawing within the solution or provide a link if you’re using an online tool.


Good luck!  
ParkHere Dev Team

