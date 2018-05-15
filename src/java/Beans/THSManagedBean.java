/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;


import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

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
@Stateful(passivationCapable=false)
public class THSManagedBean {
               
   
        TicketsHandler th = new TicketsHandler();
        ArrayList<Personnel> personnels = new ArrayList<>();
        Ticket ticket = new Ticket();
        Personnel chosenPersonnel = new Personnel();
        ArrayList<Ticket> tickets = new ArrayList<>();
        ArrayList<ProcessLead> processLeads = new ArrayList<>();
        int personnelNo;

    public int getPersonnelNo() {
        return personnelNo;
    }

    public void setPersonnelNo(int pNo) {
        this.personnelNo = pNo;
        System.out.println("Hej från inläsningen");
    }
        
        

        

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
   
        tickets = th.readTickets("getAllTickets()");
//        processLeads = th.readProcessLead();
        //TODO: Temporary
        ticket = tickets.get(0);
    }
    public ArrayList<Ticket> getTickets()   {
        return tickets;
    }
    
    public ArrayList<Personnel> getPersonnels()  {
        //empty th personnel
        th.setPersonnels(new ArrayList<>());
        //read th personnel
        th.readPersonnel();
        //get th personnel
        personnels = th.getPersonnels();
        
        return personnels;
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
    public void setChosenPersonnel(int pNo) {

        personnelNo = pNo;
        System.out.println("Hej från inläsningen");
    }
    public void buttonTest(){
        System.out.println("Buttonclick");
        System.out.println("Hej"+personnelNo);
    }
    
    

    
}
