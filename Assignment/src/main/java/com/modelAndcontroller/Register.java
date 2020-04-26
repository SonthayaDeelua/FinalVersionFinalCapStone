package com.modelAndcontroller;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class Register {
	
	private long id;
	
	
	@NotBlank(message = "Email is a required field.")
	@Email(message = "Please enter a valid email address.")
	private String email;
	
	private String hexaId;
	
	@NotBlank(message = "First Name is a required field.")
	private String firstName;
	
	private String lastName;
	
	private String phoneNumber;
		
		
		
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getHexaId() {
			return hexaId;
		}

		public void setHexaId(String hexaId) {
			this.hexaId = hexaId;
		}
		
		
		

}
