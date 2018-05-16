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
import TicketModel.Personnel;
import TicketModel.ProcessLead;
import TicketModel.Ticket;
import TicketModel.TicketsHandler;


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
    private int personnelNo;
  
    /**
     * Creates a new instance of THSManagedBean
     */
    public THSManagedBean() {
        init();
    }
    
    public void init() {
        tickets = th.readTickets("getAssignedTickets");
//        processLeads = th.readProcessLead();
        //TODO: Temporary
        ticket = tickets.get(0);
    }
    
    public void ticketAssign(Ticket ticket)    {
        ticket.setStatus("WORKER");
        th.updateTicket(ticket);
        th.emptyTickets();
        init();
    }
    
    public ArrayList<Ticket> getTickets()   {
        tickets = th.getTickets();
        
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
    public void setChosenPersonnel(Personnel per) {
        this.chosenPersonnel = per;
    }    
    public void buttonAssign(){
        System.out.println("Buttonclick");
        System.out.println("Hej"+personnelNo);
    }
    
    public int getPersonnelNo() {
        return personnelNo;
    }

    public void setPersonnelNo(int pNo) {
        this.personnelNo = pNo;
        this.ticket.setPersonellNo(pNo); //TODO research the need for this
        ticketAssign(ticket);
        System.out.println("Hej från inläsningen");
    }
    public TicketsHandler getTh() {
        return th;
    }

    public void setTh(TicketsHandler th) {
        this.th = th;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
