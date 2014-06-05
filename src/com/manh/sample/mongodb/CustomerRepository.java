package com.manh.sample.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.manh.sample.Person;

@Repository
public interface CustomerRepository extends MongoRepository<Person, String> {

    public Person findByFirstName(String firstName);
    public List<Person> findByLastName(String lastName);

}
