package ths;

import java.util.ArrayList;

/**
 *
 * @author Björn Bergdahl
 * @author Andreas Kuoppa
 */
public class TH {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Example of usage of TestDb
        
        // Test cases for runSql
        // String sql = "SELECT * FROM task";
        
        // Test cases for runSp
//        String sp0 = "getAllTickets()";
//        String sp1 = "getUnassignedTickets()";
//        String sp2 = "getComments(1)";
//        String sp3 = "getTasks(1)";
//        String sp4 = "addComment(1, 'I added this comment')";   
//        String sp5 = "addTask(2, 'This needs to be done', 'dunnit like this', 30, 0)"; //adds task
//        String sp6 = "setTicketCategory(4, 'NETWORK')";
//        String sp7 = "setTicketStatus(1, 'ASSIGNED')";
//        String sp8 = "writeTask(2, 'This needs to be done', 'dunnit like this', 30, 15)"; //updates existing task
        
        // TestDb.runSql(sql);
        // TestDb.runSp(sp4);
        // TestDb.runSp(sp2);
        
        /*
        TestDb.runSql(sql);
        TestDb.runSp(sp3);*/
        
        // Tests th.readTickets() which in turn tests getAllTickets(), getComments() and getTasks();
        TicketsHandler th = new TicketsHandler();
        // To call unassigned switch string into readTickets
        ArrayList<Ticket> tkts = th.getTickets();
        th.readTickets("getAllTickets()");

        for (Ticket tktit : tkts){
            System.out.println("\n----- Ticket -----");
            System.out.println(tktit.getTktNo());
            System.out.println(tktit.getName());
            System.out.println(tktit.getPersonellNo());
            System.out.println(tktit.getProcessLeadNo());
            System.out.println(tktit.getStatus());
            
            ArrayList<Comment> cmnts = tktit.getComments();
            for (Comment cmnt : cmnts)  {
                System.out.println("-----Comment-----");
                System.out.println(cmnt.getCommentNo());
                System.out.println(cmnt.getTktNo());
                System.out.println(cmnt.getText());
            }
            ArrayList<Task> tsks = tktit.getTasks();
            for (Task tsk : tsks) {
                System.out.println("-----Tasks-----");
                System.out.println(tsk.getTaskNo());
                System.out.println(tsk.getName());
                System.out.println(tsk.getTimeBudgetMinutes());
                System.out.println(tsk.getTimeSpentMinutes());
                System.out.println(tsk.getTktNoTsk());
            }

        } 
//        String comment = ("Dickus¤0033-12-24¤New comment from TH");
//        th.addComment(1, comment);
        
        System.out.println("--------Size: "+tkts.size());
        Ticket ticket = tkts.get(2);
        
        ticket.setCategory("NETWORK");

        ticket.setStatus("ASSIGNED");
        ArrayList<Personnel> pers = th.readPersonnel();
        ;
        for (Personnel per : pers){
            System.out.println(per.getFirstName());
        }
// adds all comments for every run...
//        ticket.addComment("New variables confound me, heading for infinity?");
       
        th.updateTicket(ticket);
        // check results in mydb
      
       
    }
    
}
