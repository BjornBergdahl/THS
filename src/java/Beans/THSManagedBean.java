/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.ArrayList;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import ths.Personnel;
import ths.ProcessLead;
import ths.Ticket;
import ths.TicketsHandler;


/**
 *
 * @author Björn
 */
@ManagedBean (name = "THSMB" , eager = true)
@RequestScoped
public class THSManagedBean {
               
   
        TicketsHandler th = new TicketsHandler();
        ArrayList<Personnel> personnels = new ArrayList<>();
        Ticket ticket = new Ticket();
        Personnel chosenPersonnel = new Personnel();
        ArrayList<Ticket> tickets = new ArrayList<>();
        ArrayList<ProcessLead> processLeads = new ArrayList<>();
        
        

        

    /**
     * Creates a new instance of THSManagedBean
     */
    public THSManagedBean() {
        init();
        System.out.println("Nice! Check for personells size below -------------------");

        System.out.println(personnels.size());
    /**
     *
     * @return tickets 
     */
        
    }
    public void init() {
        personnels = th.readPersonnel();
        tickets = th.readTickets("getAllTickets()");
        processLeads = th.readProcessLead();
    }
    public ArrayList<Ticket> getTickets()   {
        return tickets;
    }
    
    public ArrayList<String> getPersonnels()  {
        personnels = th.readPersonnel();
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
        this.chosenPersonnel = chosenPersonnel;
    }
    
    

    
}
