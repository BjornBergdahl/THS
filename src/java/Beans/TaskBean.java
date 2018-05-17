/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import TicketModel.Task;
import TicketModel.Ticket;
import TicketModel.TicketsHandler;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Björn
 */
@Named(value = "taskBean")
@Dependent
public class TaskBean {

    @EJB
    private THSManagedBean tHSManagedBean;
        Task task = new Task();
        ArrayList<Ticket> tickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        ArrayList<Task> tasks = new ArrayList<>();
        TicketsHandler th = new TicketsHandler();
    /**
     * Creates a new instance of TaskBean
     */
    public TaskBean() {
        initTaskview();
    }
   

    private void initTaskview() {
        ticket = getTicket();
        tasks = ticket.getTasks();
    }
    
    public Ticket getTicket()  {
        ticket = tHSManagedBean.getTicket();
        if (ticket.getName().equals(""))    {
            ticket = defaultTicket();
        }
            
        
        return ticket;
    }
    
    public Ticket defaultTicket()   {
        ticket.setTktName("No tickets assigned");
        ticket.setCategory("NONE");
        ticket.setStatus("Nothing assigned");
        ticket.setTktNo(999);
        return ticket;
    }
    
    
}
