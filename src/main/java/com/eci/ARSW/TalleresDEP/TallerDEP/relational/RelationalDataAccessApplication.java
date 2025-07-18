package com.eci.ARSW.TalleresDEP.TallerDEP.relational;

import com.eci.ARSW.TalleresDEP.TallerDEP.Customer;
import com.eci.ARSW.TalleresDEP.TallerDEP.CustomerJPA;
import com.eci.ARSW.TalleresDEP.TallerDEP.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RelationalDataAccessApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        splitUpNames.forEach(name ->
                log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                        "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                        (rs, rowNum) -> new Customer(rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name")),
                        "Josh")
                .forEach(customer -> log.info(customer.toString()));
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            repository.save(new CustomerJPA("Jack", "Bauer"));
            repository.save(new CustomerJPA("Chloe", "O'Brian"));
            repository.save(new CustomerJPA("Kim", "Bauer"));
            repository.save(new CustomerJPA("David", "Palmer"));
            repository.save(new CustomerJPA("Michelle", "Dessler"));

            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            repository.findAll().forEach(customer -> log.info(customer.toString()));
            log.info("");

            CustomerJPA customer = repository.findById(1L);
            log.info("Customer found with findById(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> log.info(bauer.toString()));
            log.info("");
        };
    }
}
