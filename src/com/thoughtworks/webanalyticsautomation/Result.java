package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.plugins.Status;

import java.util.ArrayList;

public class Result {
    private ArrayList<String> listOfErrors;
    private Status verificationStatus;

    public Result(Status verificationStatus, ArrayList<String> listOfErrors) {
        this.verificationStatus = verificationStatus;
        this.listOfErrors = listOfErrors;
    }

    public Result(ArrayList<String> errorList) {
        this.listOfErrors = errorList;
        if (this.listOfErrors.size()!=0){
            this.listOfErrors.add(0, "Following tags found missing: ");
            verificationStatus = Status.FAIL;
        }
        else {
            verificationStatus = Status.PASS;
        }
    }

    public ArrayList<String> getListOfErrors() {
        return this.listOfErrors;
    }

    public Status getVerificationStatus() {
        return this.verificationStatus;
    }
}
