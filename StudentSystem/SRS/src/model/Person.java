package model;

public abstract class Person {

	private String name;
	private String ssn;

private String password;
	
	public Person(String ssn,String name,String password) {
		this.setSsn(ssn);
		this.setName(name);
		this.setPassword(password);
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSsn(String s) {
		ssn = s;
	}
	
	public String getSsn() {
		return ssn;
	}

	public void setPassword(String p) {
		password = p;
	}
	
	public String getPassword() {
		return password;
	}
	
}	