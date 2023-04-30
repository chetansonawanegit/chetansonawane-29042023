package com.avisys.cim;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	public Customer addCustomer(Customer customer)
	{
		return customerRepo.save(customer);
	}

}
