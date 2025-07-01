package com.eci.ARSW.TalleresDEP.TallerDEP;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerJPA, Long> {

    List<CustomerJPA> findByLastName(String lastName);

    CustomerJPA findById(long id);
}