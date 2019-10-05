# Product-Package Manager 

The API must support the following:
o Create a package o Retrieve a package o Update a package o Delete a package o List all packages
• I should be able to specify the currency (taking into account the current exchange rate)
o API should default to USD if no currency is specified
o Free API services such as https://exchangeratesapi.io/ offer the ability to query existing
exchange rates
• A package should have the following attributes:
o ID
o Name
o Description
o Products (one or more)
o Price (total of product prices)

## Run the test
 mvn clean test

## Executing the application

- Navigate to terminal and run the command below:

cd api 
mvn spring-boot:run
cd ../ui
npm start

- go the http://localhost:3000/

## Prepare for production
The project is not production ready. Embedded db is used for demo and testing purpose. You can run spring config server, store database credentials in it. You can pass spring production 
profile to run application and the application fetch the credentials from cloud server.
  

## Make service high available
You can launch multiple EC2 instances from your AMI and then use Elastic Load Balancing to distribute incoming traffic for your application across these EC2 instances.This increases the 
availability of the application. Placing the instances in multiple Availability Zones also improves the fault tolerance in the application. 
If one Availability Zone experiences an outage, traffic is routed to the other Availability Zone.





  
