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
     *List of personnel handling Tickets
     */
    protected static ArrayList<Personnel> personnel = new ArrayList<>();
    protected static ArrayList<ProcessLead> processLead = new ArrayList<>();
    
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
    
    //handles writing of whole Ticket in database. Updates category, status and comments (tasks handled separatly)
    public void updateTicket(Ticket tkt)   {    
        ArrayList<Comment> comments = tkt.getComments();
        ArrayList<Task> tasks = tkt.getTasks();
        try{
            String catsql = "setTicketCategory("+tkt.getTktNo()+", '"+tkt.getCategory()+"')";
            DbConnection.runSp(catsql);
            String statsql = "setTicketStatus("+tkt.getTktNo()+", '"+tkt.getStatus()+"')";
            DbConnection.runSp(statsql);
            //TODO refactor as Tasks below!
            for (Comment comment : comments)    {
                String comsql = "addComment("+tkt.getTktNo()+", '"+comment.getText()+"')";                     
                if (!(comment.getCommentNo()>0))  {                                                         //Hade vänt logiken fel, itererade in alla gamla inga nya kommentarer...
                    DbConnection.runSp(comsql);  
                }
            }
            for (Task task : tasks)    {
                tkt.addTask(task);
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
