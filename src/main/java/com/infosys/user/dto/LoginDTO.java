package com.infosys.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginDTO {
	@Email(message = "Enter valid email address")
    @NotNull(message = "Enter email address")
	String email;
	@NotNull(message = "Enter valid password")
	String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", password=" + password + "]";
	}
	
}
