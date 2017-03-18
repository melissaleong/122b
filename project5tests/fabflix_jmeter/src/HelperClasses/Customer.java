package HelperClasses;

public class Customer {
	private int id;
	private String f_name;
	private String l_name;
	private String cc_id;
	private String address;
	private String email;
	private String password;
	public Customer(int id, String f_name, String l_name, String cc_id, String address, String email, String password) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.cc_id = cc_id;
		this.address = address;
		this.email = email;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getL_name() {
		return l_name;
	}
	public void setL_name(String l_name) {
		this.l_name = l_name;
	}
	public String getCc_id() {
		return cc_id;
	}
	public void setCc_id(String cc_id) {
		this.cc_id = cc_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
}

