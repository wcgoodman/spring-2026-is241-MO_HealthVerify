package com.mohealthverify.dto;

public class ProfileUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;

    private Boolean isMissouriResident;
    private String dateOfBirth;

    private String address1;
    private String address2;
    private String addressCity;
    private String addressState;
    private String addressZipCode;

    // GETTERS & SETTERS

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getIsMissouriResident() { return isMissouriResident; }
    public void setIsMissouriResident(Boolean isMissouriResident) { this.isMissouriResident = isMissouriResident; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getAddressCity() { return addressCity; }
    public void setAddressCity(String addressCity) { this.addressCity = addressCity; }

    public String getAddressState() { return addressState; }
    public void setAddressState(String addressState) { this.addressState = addressState; }

    public String getAddressZipCode() { return addressZipCode; }
    public void setAddressZipCode(String addressZipCode) { this.addressZipCode = addressZipCode; }
}