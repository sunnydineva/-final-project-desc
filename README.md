# **Project Description**

This project is a reservation system for a car rental company. The system allows users to reserve cars, update their reservations, and cancel them if necessary.

The project has a RESTful API with the following endpoints:

## **USER**

POST /users/save

GET /users/email/{email}

GET /users/{id}

DELETE /users/{id}

PUT /users/update

PUT /users/psd


## **CAR**

GET /cars/{id}

POST /cars/save

PUT /cars/price

DELETE /cars/{id}


## **RESERVATION**

POST /reservations/save

GET /reservations/{id}

PUT /reservations/update/car

PUT /reservations/update/period

GET /reservations/period

DELETE /reservations/{id}

## **Development**

The project is built using Spring Boot, with Spring Data JPA for database communication and validation, and Hibernate as the JPA implementation.

## **Credits**

This project was developed by Sunny Dineva as part of Java Advanced Course at Advance Academy.
