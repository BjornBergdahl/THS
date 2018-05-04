/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//BB
package ths;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Björn
 * @author Andreas
 */
public class TicketsHandler {
    protected static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    
    
    
  /**
    * reads String command that defines if all, unassigned or another 
    * selection of tickets with all comments and tasks will be read into tickets
    */
    public void readTickets(String command) {
        
        try{

            ResultSet results = DbConnection.runSp(command);

            while (results.next())  {
                Ticket tkt = new Ticket();
                tkt.setTktNo(results.getInt("tktNo"));
                tkt.setPersonellNo(results.getInt("processLeadNo"));
                tkt.setstaffNo(results.getInt("staffNo"));
                tkt.setTktName(results.getString("name"));
                tkt.setStatus(results.getString("status"));
                tkt.setCategory(results.getString("category"));
                System.out.println("this effin ticket " + tkt.getTktNo());
                tkt.readComments();
                tkt.readTasks();
                tickets.add(tkt);
            }
            results.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        }   
    }
    
    
    // only add, no write method. comments only added, never changed. (INSERT)
    public void addComment(int tktNo, String textInput) {
        Ticket ticket = tickets.get(tktNo-1);   //Ticket no 1 is placed in index 0 in ArrayList
        ticket.addComment(textInput);   
        
    }
    // write for changing a task (UPDATE)
    public void writeTask(int tktNo, int tskNo, String name, int timeBudget, int timeSpent) {
        Ticket ticket = tickets.get(tktNo-1); //Ticket no 1 is placed in index 0 in ArrayList
        ticket.writeTask(tskNo, name, timeBudget, timeSpent);       
    }
    // add for adding new task (INSERT)
    public void addTask(int tktNo, String name, String text, int timeBudget, int timeSpent) {
        Ticket ticket = tickets.get(tktNo-1); //Ticket no 1 is placed in index 0 in ArrayList
        ticket.addTask(ticket, name, timeBudget, timeSpent);        
    }
    
    //handles writing of whole Ticket in database. Updates category, status and comments (tasks handled separatly)
    public void updateTicket(Ticket tkt)   {    
        ArrayList<Comment> comments = tkt.getComments();
        ArrayList<Task> tasks = tkt.getTasks();
        try{
            String catsql = "setTicketCategory("+tkt.getTktNo()+", '"+tkt.getCategory()+"')";
            DbConnection.runSp(catsql);
            String statsql = "setTicketStatus("+tkt.getTktNo()+", '"+tkt.getStatus()+"')";
            DbConnection.runSp(statsql);
            for (Comment comment : comments)    {
                String comsql = "addComment("+tkt.getTktNo()+", '"+comment.getText()+"')";                     
                if (!(comment.getCommentNo()>0))  {                                                         //Hade vänt logiken fel, itererade in alla gamla inga nya kommentarer...
                    DbConnection.runSp(comsql);  
                }
            }
            for (Task task : tasks)    {
                String tsksql = "addTask("+tkt.getTktNo()+", '"+task.getName()+"', "+task.getTimeBudgetMinutes()+", "+task.getTimeSpentMinutes()+")";                     
                if (!(task.getTaskNo() > 0))  {
                    DbConnection.runSp(tsksql);  
                }
            }
            
            
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        } 
    }

    
    
        
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
     
    public void addTicket(Ticket tkt) {
        tickets.add(tkt);    
    }
    
    // Was added by Andreas
    public void emptyTickets() {
        tickets.clear();
    }
    
    
    
    
    


    
    
    
}
