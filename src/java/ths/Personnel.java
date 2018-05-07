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
public class Personnel {
    private int staffNo;
    private String firstName;
    private String lastName;
    private String competence;

    /**
     * @return the staffNo
     */
    public int getStaffNo() {
        return staffNo;
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

    /**
     * @return the competence
     */
    public String getCompetence() {
        return competence;
    }

    /**
     * @param competence the competence to set
     */
    public void setCompetence(String competence) {
        this.competence = competence;
    }
    
}
