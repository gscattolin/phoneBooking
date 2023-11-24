# Spring Boot Phone Booking Example Project
<img src="https://img.shields.io/badge/SpringBoot-COLOR.svg?logo=LOGO">
<img src="https://img.shields.io/badge/Java-blue">

This is a sample Java / Maven / Spring Boot (version 1.5.6) application of phone booking.

## How to Run

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by :
```
        mvn spring-boot:run 
```
* Check the stdout or boot_example.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2023-11-24T10:09:04.781+04:00  INFO 11676 --- [           main] c.e.p.PhoneBookingApplication            : Starting PhoneBookingApplication using Java 21.0.1 with PID 11676 (xxxx\phonebooking\target\classes started by xxx in xxxx\phonebooking)
2023-11-24T10:09:04.784+04:00  INFO 11676 --- [           main] c.e.p.PhoneBookingApplication            : No active profile set, falling back to 1 default profile: "default"
2023-11-24T10:09:05.556+04:00  INFO 11676 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2023-11-24T10:09:05.565+04:00  INFO 11676 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2023-11-24T10:09:05.565+04:00  INFO 11676 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.15]
```
Here are some endpoints you can call:

### Get all available phones.

```
http://localhost:8080/phones
```

### Get detail of a  phone by id.

```
http://localhost:8080/phones/1
```

### Book a phone by id specifying a client name.

```
POST 
http://localhost:8080/phones/available/1
Accept: application/json
Content-Type: application/json

{
"name" : "Rick Sanchez"
}
```

### Return a phone by id specifying a client name.

```
POST 
http://localhost:8080/phones/booked/1
Accept: application/json
Content-Type: application/json

```
