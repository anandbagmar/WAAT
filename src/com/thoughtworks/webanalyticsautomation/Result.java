package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import java.util.ArrayList;

public class Result {
    private ArrayList<String> listOfErrors;
    private Status verificationStatus;
    private String actionName;

    public Result(String actionName, Status verificationStatus, ArrayList<String> listOfErrors) {
        this.verificationStatus = verificationStatus;
        setupResult(actionName, listOfErrors);
    }

    public Result(String actionName, ArrayList<String> errorList) {
        setupResult(actionName, errorList);
    }

    public ArrayList<String> getListOfErrors() {
        return this.listOfErrors;
    }

    public Status getVerificationStatus() {
        return this.verificationStatus;
    }

    private void setupResult(String actionName, ArrayList<String> listOfErrors) {
        this.actionName = actionName;
        this.listOfErrors = listOfErrors;
        if (this.listOfErrors.size()!=0){
            this.listOfErrors.add(0, "Action Name: " + this.actionName);
            verificationStatus = Status.FAIL;
        }
        else if (verificationStatus != Status.SKIPPED) {
            verificationStatus = Status.PASS;
        }        
    }
}
