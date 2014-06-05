package com.manh.fedex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manh.sample.Person;
import com.manh.sample.mongodb.CustomerRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private MongoOperations mongoOperation;
	
	@Autowired
	private CustomerRepository customerRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Person getPerson() {
		Person p = new Person();
		p.setFirstName("Palanivelrajan");
		p.setLastName("Rajan");

		mongoOperation.save(p);

		return p;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public Person listPerson() {

		// query to search user
		Query searchUserQuery = new Query(Criteria.where("firstName").is(
				"Palanivelrajan"));

		// find the updated user object
		Person person = mongoOperation.findOne(searchUserQuery, Person.class);

		System.out.println("3. updatedUser : " + person);

		System.out.println(person.getId());

		return person;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/list1")
	public List<Person> listPerson1() {
		
		return customerRepository.findAll();
		
		

	}

}
