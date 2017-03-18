package HelperClasses;

public class Employee {
	public String email;
	public String password;
	public String fullname;
	
	public Employee(String email, String password){
		this.email=email;
		this.password=password;
	}
	
	public Employee(String email, String password, String fullname){
		this.email=email;
		this.password=password;
		this.fullname=fullname;
	}
	
	
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
}
