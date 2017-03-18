package HelperClasses;
import java.sql.Date;
public class Creditcard {
	private String id;
	private String f_name;
	private String l_name;
	private Date exp;
	public Creditcard(String id, String f_name, String l_name, Date exp) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.exp = exp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Date getExp() {
		return exp;
	}
	public void setExp(Date exp) {
		this.exp = exp;
	}
	
}
