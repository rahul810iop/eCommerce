package com.bookstall.dao;

import java.util.Date;
import java.util.List;

import com.bookstoredb.entity2.Customer;

public class CustomerDAO extends JpaDAO<Customer> implements GenericDAO<Customer> {
	
	@Override
	public Customer create(Customer customer) {
		customer.setRegisterDate(new Date());
		return super.create(customer);
	}

	@Override
	public Customer update(Customer customer) {
		return super.update(customer);
	}

	@Override
	public Customer get(Object id) {
		return super.find(Customer.class, id);
	}

	@Override
	public void delete(Object id) {
		super.delete(Customer.class, id);
	}

	@Override
	public List<Customer> listAll() {
		return super.findWithNamedQuery("Customer.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Customer.countAll");
	}

}