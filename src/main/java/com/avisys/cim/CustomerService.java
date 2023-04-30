package com.avisys.cim;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	
	// Requirement 2
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
	
	
	
// Requirement 5
	public int deleteCustomerByMobileNumber(String mobileNumber) {

		Customer customer =  customerRepo.findDistinctByMobileNumber(mobileNumber);
		
		if(customer != null)
		{
			customerRepo.delete(customer);
			return 1;
		}
		return 0;
	}

// Requirement 6 Update Mobile Number
	public Customer updateMobileNumber(Long id, String mobileNumber,String updatemobileNumber) {
		
		Optional<Customer> customer = customerRepo.findById(id);
		
		if(customer.isPresent())
		{
			List<String> list = customer.get().getMobileNumber();

			for (String number : list) {
				if(number.equals(mobileNumber))
				{
					number = updatemobileNumber;
				}
			}
			
			customer.get().setMobileNumber(list);
			
			customerRepo.save(customer.get());
		}
		
		return null;
	}


	public int deleteMobileNumber(String mobileNumber) {
		
		Customer customer =  customerRepo.findDistinctByMobileNumber(mobileNumber);
		if(customer != null)
		{
			customer.getMobileNumber().remove(mobileNumber);
			customer.setMobileNumber(customer.getMobileNumber());
			customerRepo.save(customer);
			
			return 1;
		}
		
		return 0;
	}

}
