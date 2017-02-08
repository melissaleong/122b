package HelperClasses;
import java.sql.Date;
public class Sale {
	private int id;
	private int c_id;
	private int m_id;
	private Date sale_date;
	public Sale(int id, int c_id, int m_id, Date sale_date) {
		super();
		this.id = id;
		this.c_id = c_id;
		this.m_id = m_id;
		this.sale_date = sale_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public Date getSale_date() {
		return sale_date;
	}
	public void setSale_date(Date sale_date) {
		this.sale_date = sale_date;
	}
	
}
