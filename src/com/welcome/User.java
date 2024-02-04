package com.welcome;
import com.google.gson.annotations.SerializedName;
public class User {
	 @SerializedName("Username")
	 private String username;
	 @SerializedName("Name")
	    private String name;
	 @SerializedName("PhoneNum")
	    private String phoneNum;
	 @SerializedName("PhoneNumLocation")
	    private String phoneNumLocation;

	    @SerializedName("RegistrationDATE")
	    private String registrationDate;
	    @SerializedName("Organization")
	    private String organization;
	    @SerializedName("Role")
	    private String role;

	    
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	   
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    
	    public String getPhoneNum() {
	        return phoneNum;
	    }

	    public void setPhoneNum(String phoneNum) {
	        this.phoneNum = phoneNum;
	    }

	   
	    public String getPhoneNumLocation() {
	        return phoneNumLocation;
	    }

	    public void setPhoneNumLocation(String phoneNumLocation) {
	        this.phoneNumLocation = phoneNumLocation;
	    }

	    
	    public String getRegistrationDate() {
	        return registrationDate;
	    }

	    public void setRegistrationDate(String registrationDate) {
	        this.registrationDate = registrationDate;
	    }

	    public String getOrganization() {
	        return organization;
	    }

	    public void setOrganization(String organization) {
	        this.organization = organization;
	    }

	    
	    public String getRole() {
	        return role;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }

}
