/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ths;

/**
 *
 * @author Björn
 */
public class ProcessLead {

    /**
     * @param processLeadNo the processLeadNo to set
     */
    public void setProcessLeadNo(int processLeadNo) {
        this.processLeadNo = processLeadNo;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    private int processLeadNo;
    private String firstName;
    private String lastName;

    /**
     * @return the processLeadNo
     */
    public int getProcessLeadNo() {
        return processLeadNo;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    
}
