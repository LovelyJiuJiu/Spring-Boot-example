package com.example.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@Transactional
public class Demo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}
	
	@Autowired
	private SessionFactory factory;
	
	@PostMapping(name = "/demo")
	public User name(@RequestBody User user1) {
		
		Session session = factory.getCurrentSession();
		session.save(user1);
		return user1;
	}
	
	@RequestMapping(name = "/demo", method = RequestMethod.GET)
	public List<User> getUser() {
		
		Session session = factory.getCurrentSession();
		return session.createQuery("from User order by id desc", User.class).setMaxResults(1).getResultList();
	}
}
