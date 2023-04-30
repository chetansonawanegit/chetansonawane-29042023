package com.avisys.cim;




import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepo extends JpaRepository<Customer, Long> {
	
	
	// For Requirement 5
	// First find Customer for Specific Mobile Number
	
	Customer findDistinctByMobileNumber(String mobileNumber);


}
