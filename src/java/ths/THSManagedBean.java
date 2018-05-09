/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ths;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author Björn
 */
@ManagedBean (name = "THSMB" , eager = true)
@SessionScoped
public class THSManagedBean {
        

        
        @EJB
        TicketsHandler th = new TicketsHandler();
        ArrayList<Personnel> personnels;
        @PostConstruct
        public void init() {
            personnels = th.readPersonnel();
        }
        
        Ticket ticket = new Ticket();
        private Personnel chosenPersonnel = new Personnel();
        ArrayList<Ticket> tickets = th.readTickets("getAllTickets()");
        ArrayList<ProcessLead> processLeads = th.readProcessLead();
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
    
    public ArrayList<String> getPersonnels()  {
        
        ArrayList<String> lastNames = new ArrayList<>();
        for (Personnel per : personnels)    {
            lastNames.add(per.getLastName());
            
        }
        return lastNames;
    }
    
    /**
     *
     * @return list of ProcessLeaders
     */
    
    
    public ArrayList<ProcessLead> getProcessLeads()  {
        ArrayList<ProcessLead> processLeads = th.readProcessLead();
        return processLeads;
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
    public void setChosenPersonnel(String lastName) {
        System.out.println("--------------"+lastName+"-----------------");
        //this.chosenPersonnel = chosenPersonnel;
    }
    
    

    
}
