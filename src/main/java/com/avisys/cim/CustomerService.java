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


	//Requirement 3
	public Customer addMobileNumber(Long id, String mobileNumber) {
		
		Optional<Customer> customer = customerRepo.findById(id);
		
		if (customer.isPresent()) {
			
			List<String> list = customer.get().getMobileNumber(); //Get List of Number
			list.add(mobileNumber); //Update List by adding new Number
			customer.get().setMobileNumber(list); // Set list
			
			//Persist Details
			customerRepo.save(customer.get());
			
		}
		return null;
	}

}
