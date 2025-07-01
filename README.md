# Availability, Scalability and Persistence

## Author: David Alfonso Barbosa Gómez

## Accessing Relational Data using JDBC with Spring

**Following this** [tutorial](https://spring.io/guides/gs/relational-data-access)

### Required Dependencies

```uml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

### Creating a Customer Object 

```java
package com.eci.ARSW.TalleresDEP.TallerDEP;

public class Customer {
  private long id;
  private String firstName, lastName;

  public Customer(long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return String.format(
        "Customer[id=%d, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }
}
```
### Store and Retrieve Data

```java

package com.eci.ARSW.TalleresDEP.TallerDEP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);

  public static void main(String args[]) {
    SpringApplication.run(RelationalDataAccessApplication.class, args);
  }

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public void run(String... strings) throws Exception {

    log.info("Creating tables");

    jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
    jdbcTemplate.execute("CREATE TABLE customers(" +
        "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

    // Split up the array of whole names into an array of first/last names
    List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
        .map(name -> name.split(" "))
        .collect(Collectors.toList());

    // Use a Java 8 stream to print out each tuple of the list
    splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

    // Uses JdbcTemplate's batchUpdate operation to bulk load data
    jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

    log.info("Querying for customer records where first_name = 'Josh':");
    jdbcTemplate.query(
        "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
        (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")), "Josh")
    .forEach(customer -> log.info(customer.toString()));
  }
}

```
### Results in command line

2025-07-01T10:45:06.202-05:00  INFO 12652 --- [TallerDEP] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.

2025-07-01T10:45:06.214-05:00  INFO 12652 --- [TallerDEP] [           main] .e.A.T.T.RelationalDataAccessApplication : Inserting customer record for John Woo

2025-07-01T10:45:06.214-05:00  INFO 12652 --- [TallerDEP] [           main] .e.A.T.T.RelationalDataAccessApplication : Inserting customer record for Jeff Dean

2025-07-01T10:45:06.214-05:00  INFO 12652 --- [TallerDEP] [           main] .e.A.T.T.RelationalDataAccessApplication : Inserting customer record for Josh Bloch

2025-07-01T10:45:06.214-05:00  INFO 12652 --- [TallerDEP] [           main] .e.A.T.T.RelationalDataAccessApplication : Inserting customer record for Josh Long

2025-07-01T10:45:06.222-05:00  INFO 12652 --- [TallerDEP] [           main] .e.A.T.T.RelationalDataAccessApplication : Querying for customer records where first_name = 'Josh':

2025-07-01T10:45:06.230-05:00  INFO 12652 --- [TallerDEP] [           main] .e.A.T.T.RelationalDataAccessApplication : Customer[id=3, firstName='Josh', lastName='Bloch']

2025-07-01T10:45:06.231-05:00  INFO 12652 --- [TallerDEP] [           main] .e.A.T.T.RelationalDataAccessApplication : Customer[id=4, firstName='Josh', lastName='Long']

2025-07-01T10:45:06.233-05:00  INFO 12652 --- [TallerDEP] [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...

2025-07-01T10:45:06.235-05:00  INFO 12652 --- [TallerDEP] [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown complet




