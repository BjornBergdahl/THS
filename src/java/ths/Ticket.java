/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ths;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Björn
 */
public class Ticket {
    
    private int processLeadNo;
    private int personellNo;
    private String name;
    private int tktNo; 
    private String status; 
    private String category;
    //    Comments and Tasks belonging to the Ticket. 
    private ArrayList<Comment> comments = new ArrayList<>();
    private ArrayList<Task> tasks = new ArrayList<>();
 
    

    
 //sets value in instance
   public void setTktNo(int tktNo) {
        this.tktNo=tktNo;
    }
    
//sets value in instance
   public void setTktName(String name) {
        this.name= name;
    }
     
//sets value in instance
    public void setPersonellNo(int pNo) {
        this.personellNo = pNo;
    }

//sets value in instance
    public void setstaffNo(int sNo) {
        this.processLeadNo = sNo;
    }
//sets value in instance
    public void setStatus(String status) {
        this.status = status;
    }
//sets value in instance
    public void setCategory(String category) {
        this.category = category;
    }
    
//      public void writeCategory(int tktNo, String category)   {
//          Ticket ticket = tickets.get(tktNo);
//          ticket.addCategory(category);
//      }
    
//    adds a new comment to ticket comments
    public void setComment(Ticket tkt, String text)    {
        Comment comment = new Comment();
        comment.setTktNo(tkt.getTktNo());
        comment.setText(text);
        comments.add(comment);
    }
    
//    public void setTask(Ticket tkt, String name, String text, int timeBudget, int timeSpent)    {
//        Task task = new Task();
//        task.setName(name);
//        task.setText(text);
//        task.setTaskNo(tkt.getTktNo());
//        task.setTimeBudgetMinutes(timeBudget);
//        task.setTimeSpentMinutes(timeSpent);
//        tasks.add(task);
//        
//    }
    
    
//    reads the Comments belonging to instance of Ticket from database
    public void readComments() {
                
        try{
            
            ResultSet results = DbConnection.runSp("getComments(" + tktNo + ")");
            
            while (results.next())  {
                Comment comment = new Comment();
                comment.setCommentNo(results.getInt("commentNo"));
                comment.setTktNo(results.getInt("tktNoCom"));
                comment.setText(results.getString("text"));
                comments.add(comment);
            }
            results.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        }   
    }
    //add new comment into database
    public void addComment (String text)    {
        String sqlinput = "addComment("+this.tktNo+",'"+text+"')";
        try{
                
                DbConnection.runSp(sqlinput);
        }
        catch (SQLException e) {
                System.out.println(e.getMessage( ));
        }      
    }

    
    // refactored
    public void writeTask (int tskNo, String name, int timeBudget, int timeSpent) { 
      String SQLCommand= "writeTask("+tskNo+", '"+name+"', '"+timeBudget+"', '"+timeSpent+"')";  //silly line '"+ !
        try{
            DbConnection.runSp(SQLCommand);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        }      
    }
    public void addTask (Task task) { //both write and add, used to add new and change existing tasks
        String addCommand= "addTask("+this.tktNo+", '"+task.getName()+"', '"+task.getTimeBudgetMinutes()+"', '"+task.getTimeSpentMinutes()+"')";  //silly line '"+ !
        try{
            DbConnection.runSp(addCommand);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        }      
    }
    
    public void deleteAllTasks()  {
        String deleteTasks = "deleteAllTasks("+this.tktNo+")";
                try{
            DbConnection.runSp(deleteTasks);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        }    
        
    }
    
//    reads the Tasks belonging to instance of Ticket from database
    public void readTasks() {
        
        try{
            
            ResultSet results = DbConnection.runSp("getTasks(" + tktNo + ")");
            
            while (results.next())  {
                Task task = new Task();
                task.setTaskNo(results.getInt("tskNo"));
                task.setTktNo(results.getInt("tktNoTsk"));
                task.setName(results.getString("name"));
                    
                task.setTimeBudgetMinutes(results.getInt("timeBudgetMinutes")); 
                task.setTimeSpentMinutes(results.getInt("timeSpentMinutes")); 
                tasks.add(task);
            }
            results.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        }   
    }
    
    

    /**
     * @return the processLeadNo
     */
    public int getProcessLeadNo() {
        return processLeadNo;
    }

    /**
     * @return the personellNo
     */
    public int getPersonellNo() {
        return personellNo;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the tktNo
     */
    public int getTktNo() {
        return tktNo;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the comments
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * @return the tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }


    
}
