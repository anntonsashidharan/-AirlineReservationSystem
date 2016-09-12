package com.ars.domain.user;

import java.util.Date;
import java.util.List;

/**
 * Created by JJ on 4/13/16.
 */
public class Employee extends UserLogin{
    private String id;
    private String nicNumber;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private Employee enteredBy;
    private List<String> fixedLineNumbers;
    private List<String> mobileNumbers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public Employee getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(Employee enteredBy) {
        this.enteredBy = enteredBy;
    }

    public List<String> getFixedLineNumbers() {
        return fixedLineNumbers;
    }

    public void setFixedLineNumbers(List<String> fixedLineNumbers) {
        this.fixedLineNumbers = fixedLineNumbers;
    }

    public List<String> getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(List<String> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }
}
