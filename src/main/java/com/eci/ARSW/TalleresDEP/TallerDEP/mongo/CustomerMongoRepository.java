package com.eci.ARSW.TalleresDEP.TallerDEP.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerMongoRepository extends MongoRepository<CustomerMongo, String> {

    public CustomerMongo findByFirstName(String firstName);
    public List<CustomerMongo> findByLastName(String lastName);

}