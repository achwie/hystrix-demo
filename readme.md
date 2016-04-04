# About

This is a small demo application for the [Hystrix library](https://github.com/Netflix/Hystrix) from Netflix. It's all about resiliency in distributed systems, so this is kind of a simple and naive e-commerce microservices application.

# Architecture

The Spring based demo application consists of a frontend webapp that integrates five business services to form a small e-commerce application. The services expose an REST interface, [Apache's HttpClient](https://hc.apache.org/) is used for communication. All business services use in memory storage for the sake of simplicity.

# Modules 

The application consists of the following business services, implemented as Maven modules:

  * `frontend`: provides an integrated frontend the services
  * `auth`: provides authentication and user management
  * `cart`: creation and management of shopping carts
  * `catalog`: product catalog
  * `order`: processes and archives orders
  * `stock`: provides an inventory system to return the stock count for items in the catalog

There are also some modules related to configuration and the more technical things:

  * `jetty-starter`: common base code to run the services as standalone apps in an embedded jetty server
  * `service-urls`: configures the addresses and ports under which the (business) services will run
  * `util`: utility classes
  
And finally some modules for testing:

  * `acceptance-tests`: a small set off user acceptance tests using the small and fine [JGiven](http://jgiven.org/)
  * `load-generator`: generates load to simulate user traffic

# Building

The whole project can be built via Maven:

    $ mvn clean install

In case you want to generate IDE files for Eclipse you can use:

    $ mvn eclipse:eclipse -Dwtpversion=2.0
    
# Running

All the business services can be run as standalone apps, only the frontend app must be run in a servlet container (didn't have the time to figure out how to make JSPs work in Jetty yet). Before you run it, make sure to create your local services configuration:

    $ cp service-urls/src/main/resources/services.properties.default service-urls/src/main/resources/services.properties
    
Then adjust the newly created `service.properties` file according to your environment (hint: you shouldn't have to).

The user acceptance tests can be run like JUnit tests. The load generator is a standalone java application and is also run most conveniently from within your IDE.
  
# Disclaimer

This is a demo application - treat the code as such.
