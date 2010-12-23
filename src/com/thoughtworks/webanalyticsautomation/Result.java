package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.plugins.Status;

import java.util.ArrayList;

public class Result {
    private ArrayList<String> listOfErrors;
    private Status verificationStatus;

    public Result(Status verificationStatus, ArrayList<String> listOfErrors) {
        this.verificationStatus = verificationStatus;
        this.listOfErrors = listOfErrors;
    }

    public Result(Status skipped) {
        this.verificationStatus = skipped;
        this.listOfErrors = new ArrayList<String>();
    }

    public ArrayList getListOfErrors() {
        return this.listOfErrors;
    }

    public Status getVerificationStatus() {
        return this.verificationStatus;
    }
}
