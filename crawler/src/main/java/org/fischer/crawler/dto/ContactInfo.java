package org.fischer.crawler.dto;

public class ContactInfo  {

	private String emailAddress;
	private String telephoneNumber;
	private String hjemmeside;
	private String leder;
	private String dagligLeder;
	private String kilde;
	
	private String klubbnavn;
	private int id;

	public ContactInfo() {

	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getHjemmeside() {
		return hjemmeside;
	}

	public void setHjemmeside(String hjemmeside) {
		this.hjemmeside = hjemmeside;
	}

	public String getLeder() {
		return leder;
	}

	public void setLeder(String leder) {
		this.leder = leder;
	}





	public String getDagligLeder() {
		return dagligLeder;
	}

	public void setDagligLeder(String dagligLeder) {
		this.dagligLeder = dagligLeder;
	}

	public String getKilde() {
		return kilde;
	}

	public void setKilde(String kilde) {
		this.kilde = kilde;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKlubbnavn() {
		return klubbnavn;
	}

	public void setKlubbnavn(String klubbnavn) {
		this.klubbnavn = klubbnavn;
	}




}