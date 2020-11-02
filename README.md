# unico-repo

Software and tools used:

1. Java 8
2. Maven 3.5
3. Spring boot 1.5.7
4. Embeded Tomcat
5. REST API
6. IntelliJ IDE
7. Active MQ
8. MySQL DB

Assumptions:
1. Java 8 and maven 3.5 installed on the machine
2. Active MQ service is running
3. MySQL DB installed and service is running


1. Create 'test' schema in MySql DB
2. Run following SQL script:

 CREATE TABLE `test`.`apimsg` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `i1` INT NULL,
 `i2` INT NULL,
 `gcd` INT NULL,
 PRIMARY KEY (`id`));

3. Build the project using 'mvn clean install' command
4. Start Active MQ service
5. Copy WAR and deploy to a server


Test:
*REST API*
http://localhost:8080/apiMsg/push?i1=4&i2=20
http://localhost:8080/apiMsg/list

*SOAP WS*
http://localhost:8080/soapapi/ws/gcd.wsdl


Username: foo
Password: bar
