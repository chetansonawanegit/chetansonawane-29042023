package com.avisys.cim;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping("/customerInfo/")
	public ResponseEntity<?> getCustomers()
	{
		List<Customer> list = customerRepo.findAll();
		
		if(!list.isEmpty())
			return Response.success(list);
		else {
			return Response.error("No Customer Found!!");
		}
		
	}
	
	@GetMapping("/customerInfo/{key}")
	public ResponseEntity<?> getCustomersInfo(@PathVariable String key)
	{
		List<Customer> list = customerRepo.findAll();
		
		
		//Using Stream API for better performance
		if(!list.isEmpty())
		{
			if(key.matches("[0-9]{10}")) // Client Search Mobile Number which Limits digits upto length 10 using regular expression
			{
				List<Customer> filteredList = list
						.stream()
						.filter(c -> c.getMobileNumber().equals(key))
						.collect(Collectors.toList());
				if(!filteredList.isEmpty())
					return Response.success(filteredList);
				else
					return Response.error("Customer Does Not Exist with Mobile Number "+key);
			}
			else { // Client Search using  firstName or LastName
				List<Customer> filteredList = list
						.stream()
						.filter(c -> c.getFirstName().toLowerCase().contains(key.toLowerCase()) || c.getLastName().toLowerCase().contains(key.toLowerCase()))
						.collect(Collectors.toList());
				if(!filteredList.isEmpty())
					return Response.success(filteredList);
				else
					return Response.error("Cutsomer Not Found");
			}		
		}
			
		else {
			return Response.error("No Customer Found!!");
		}
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer)
	{

		Customer newCustomer = customerService.addCustomer(customer);
		
		if(newCustomer != null)
			return Response.success(newCustomer);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create Customer. Mobile number already present.");
	}



	// Requirement 3
	@PutMapping("/addMobileNumber/{id}")
	public ResponseEntity<?> addMobileNo(@PathVariable Long id ,@RequestBody String mobileNumber)
	{

		Customer newCustomer = customerService.addMobileNumber(id,mobileNumber);
		
		if(newCustomer != null)
			return Response.success(newCustomer);
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to Add mobile Number. Mobile number already present.");
	}


}
