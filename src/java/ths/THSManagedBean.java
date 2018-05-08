/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ths;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author Björn
 */
@ManagedBean (name = "THSManagedBean" , eager = true)
@SessionScoped
public class THSManagedBean {
        TicketsHandler th = new TicketsHandler();
        Ticket ticket = new Ticket();
        private Personnel chosenPersonnel = new Personnel();
        ArrayList<Ticket> tickets = th.readTickets("getAllTickets()");
        ArrayList<Personnel> personnels = th.readPersonnel();
        ArrayList<ProcessLead> processLead = th.readProcessLead();
    /**
     * Creates a new instance of THSManagedBean
     */
    public THSManagedBean() {
        System.out.println("Nice!");
        
    /**
     *
     * @return tickets 
     */
        
    }
    
    public ArrayList<Ticket> getTickets()   {
        return tickets;
    }
    
    public ArrayList<Personnel> getPersonnels()  {
        ArrayList<Personnel> personnels = th.readPersonnel();
        return personnels;
    }
    
    public ArrayList<ProcessLead> getProcessLeads()  {
        return processLead;
    }

    /**
     * @return the chosenPersonnel
     */
    public Personnel getChosenPersonnel() {
        return chosenPersonnel;
    }

    /**
     * @param chosenPersonnel the chosenPersonnel to set
     */
    public void setChosenPersonnel(Personnel chosenPersonnel) {
        this.chosenPersonnel = chosenPersonnel;
    }
    
    

    
}
