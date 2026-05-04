package com.mohealthverify.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "applicant_profile")
public class ApplicantProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_profile_id")
    private Long applicantProfileId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "profile_last_updated")
    private OffsetDateTime profileLastUpdated;

    @Column(name = "is_missouri_resident")
    private Boolean isMissouriResident;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_city")
    private String addressCity;

    @Column(name = "address_state")
    private String addressState;

    @Column(name = "address_zip_code")
    private String addressZipCode;

    public Long getApplicantProfileId() { return applicantProfileId; }
    public void setApplicantProfileId(Long applicantProfileId) { this.applicantProfileId = applicantProfileId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public OffsetDateTime getProfileLastUpdated() { return profileLastUpdated; }
    public void setProfileLastUpdated(OffsetDateTime profileLastUpdated) { this.profileLastUpdated = profileLastUpdated; }

    public Boolean getIsMissouriResident() { return isMissouriResident; }
    public void setIsMissouriResident(Boolean isMissouriResident) { this.isMissouriResident = isMissouriResident; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

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