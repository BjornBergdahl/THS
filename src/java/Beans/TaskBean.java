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
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Björn
 */
@Named(value = "taskBean")
@Dependent
public class TaskBean {
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
        th.readTickets("getAssignedTickets()");
        
        
    }
    
}
