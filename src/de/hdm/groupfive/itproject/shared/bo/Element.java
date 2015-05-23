package de.hdm.groupfive.itproject.shared.bo;

import java.sql.Timestamp;

public class Element extends BusinessObject {
	private static final long serialVersionUID = 1L;
	
	private String name ="";
	private String description ="";
	private String materialDescription ="";
	private Timestamp creationDate;
	private Timestamp modifyDate;
	private User lastUser;
	

	public void setNameID(String string) {
		// TODO Auto-generated method stub
		/**
		   *Setzen des Namens des Bauteils
		   */
		this.name = string;
		
	}

	public void setDescriptionID(String string) {
		// TODO Auto-generated method stub
		/**
		   * Setzen der Beschreibung des Bauteils
		   */
		this.description = string;
		
	}
	
	public void setMaterialDescriptionID(String string) {
		// TODO Auto-generated method stub
		/**
		   * Setzen der Materialbeschreibung des Bauteils
		   */
		this.materialDescription = string;
		
	}


	public void setModifyDateID(Timestamp timestamp) {
		// TODO Auto-generated method stub
		/**
		   * 
		   */
		this.modifyDate = timestamp;
		
	}

	public void setCreationDateID(Timestamp timestamp) {
		// TODO Auto-generated method stub
		this.creationDate = timestamp;
	}
	
	public void setUserID(User user) {
		// TODO Auto-generated method stub
		this.lastUser = user;
		
	}

	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getMaterialdescription(){
		return this.materialDescription;
	}
	
	public Timestamp getCreationDate(){
		return this.creationDate;
	}
	
	public Timestamp getModifyDate(){
		return this.modifyDate;
	}
	
	public User getUser(){
		return this.lastUser;
	}

}
