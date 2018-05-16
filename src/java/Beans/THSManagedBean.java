/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;


import TicketModel.Comment;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import TicketModel.Personnel;
import TicketModel.ProcessLead;
import TicketModel.Task;
import TicketModel.Ticket;
import TicketModel.TicketsHandler;
import java.util.Iterator;


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
    Comment comment = new Comment();
    Task task = new Task();
    private Personnel chosenPersonnel = new Personnel();
    ArrayList<Ticket> tickets = new ArrayList<>();
    ArrayList<ProcessLead> processLeads = new ArrayList<>();
    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<Comment> comments = new ArrayList<>();
    private int personnelNo;
    private String commentString;
    TaskBean taskControll = new TaskBean();


  


    /**
     * Creates a new instance of THSManagedBean
     */
    public THSManagedBean() {
        init();
    }
    
    public void init() {
        tickets = th.readTickets("getAssignedTickets()");
        tickets = getTickets();
        ticket = tickets.get(0);
        commentString = createCommentString(ticket.getComments());
        personnels = getPersonnels();
        }
    
    public void ticketAssign(Ticket ticket)    {
        ticket.setStatus("WORKER");
        th.updateTicket(ticket);
        th.emptyTickets();
        init();
    }
    
    public ArrayList<Ticket> getTickets()   {
        tickets = th.getTickets();
        tickets=sortedTickets(tickets);
        System.out.println("----------------------------"+tickets.size());
        return tickets;
    }
    
    public ArrayList<Ticket> sortedTickets(ArrayList<Ticket> tick) {
    System.out.println("Before TsortTicket: "+tick.size());
    Iterator<Ticket> iter = tick.iterator();

    while (iter.hasNext()) {
        Ticket t = iter.next();

        if (!t.getCategory().equals("NETWORK")){
            iter.remove();
        }
    }

    System.out.println("After Tsort: "+tick.size());

    return tick;
    }
    
    public ArrayList<Personnel> getPersonnels()  {
        //empty th personnel
        th.setPersonnels(new ArrayList<>());
        //read th personnel
        th.readPersonnel();
        //get th personnel
        personnels = th.getPersonnels();
        personnels = sortedPersonnel(ticket.getCategory(), personnels);
        setChosenPersonnel(personnels.get(0));
        return personnels;
    }
    
    public ArrayList<Personnel> sortedPersonnel(String cat, ArrayList<Personnel> pers) {
        System.out.println("Before sort: "+pers.size());
        Iterator<Personnel> iter = pers.iterator();

        while (iter.hasNext()) {
            Personnel p = iter.next();

            if (!p.getCompetence().equals(cat)){
                iter.remove();
            }
        }

        System.out.println("After sort: "+pers.size());
        
        return pers;
    }
    
    
    
    public String getCommentString() {
        
        return commentString;
    }


    

    public String createCommentString(ArrayList<Comment> comments) {
        String text = "";
        for (Comment comment : comments) {
            
            String seperator = "¤";
            String allText = comment.getText();
            String name = allText.split(seperator)[0];
            String date = allText.split(seperator)[1];
            String commentText = allText.split(seperator)[2];
            
            text = date + ": " + name + "\n" + commentText + "\n\n" +text;
            }
        return text;
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
    public TaskBean getTaskcontroll() {
        return taskControll;
    }

    public void setTaskcontroll(TaskBean taskcontroll) {
        this.taskControll = taskcontroll;
    }

}
