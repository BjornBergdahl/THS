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
import javax.ejb.EJB;

/**
 *
 * @author Björn
 * @author Andreas
 */
@EJB
public class TicketsHandler {


    protected static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    protected static ArrayList<Personnel> personnels = new ArrayList<>();
    protected static ArrayList<ProcessLead> processLeads = new ArrayList<>();
    
  /**
    * reads String command that defines if all, unassigned or another 
    * selection of tickets with all comments and tasks will be read into tickets
    */
    public ArrayList<Ticket> readTickets(String command) {
        
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
    return tickets;
    }
    
    public ArrayList<Personnel> readPersonnel() {
            try{

                ResultSet results = DbConnection.runSp("getPersonnel");

            while (results.next())  {
                Personnel peon = new Personnel();
                peon.setStaffNo(results.getInt("staffNo"));
                peon.setFirstName(results.getString("fName"));
                peon.setLastName(results.getString("lName"));
                peon.setCompetence(results.getString("competence"));
                personnels.add(peon);
                
            }
            results.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        }  
        return personnels;    
    }

        public ArrayList<ProcessLead> readProcessLead() {
            try{

                ResultSet results = DbConnection.runSp("getProcessLead");

            while (results.next())  {
                ProcessLead boss = new ProcessLead();
                boss.setProcessLeadNo(results.getInt("processLeadNo"));
                boss.setFirstName(results.getString("fName"));
                boss.setLastName(results.getString("lName"));
                processLeads.add(boss);
            }
            results.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage( ));
            }  
            return processLeads;
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
