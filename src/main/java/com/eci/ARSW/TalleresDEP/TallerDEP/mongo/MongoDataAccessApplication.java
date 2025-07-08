package com.eci.ARSW.TalleresDEP.TallerDEP.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongoDataAccessApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MongoDataAccessApplication.class);

    @Autowired
    private CustomerMongoRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(MongoDataAccessApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        // save a couple of customers
        repository.save(new CustomerMongo("Alice", "Juanito"));
        repository.save(new CustomerMongo("Bob", "Smith"));

        // fetch all customers
        log.info("Customers found with findAll():");
        log.info("-------------------------------");
        for (CustomerMongo customer : repository.findAll()) {
            log.info(customer.toString());
        }

        // fetch an individual customer
        log.info("Customer found with findByFirstName('Alice'):");
        log.info("--------------------------------");
        log.info(repository.findByFirstName("Alice").toString());

        log.info("Customers found with findByLastName('Juanito'):");
        log.info("--------------------------------");
        for (CustomerMongo customer : repository.findByLastName("Juanito")) {
            log.info(customer.toString());
        }
    }
}

