/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickethandler;

import java.io.IOException;
import java.net.URL;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import TicketModel.Comment;
import TicketModel.Task;
import TicketModel.Ticket;
import TicketModel.TicketsHandler;

/**
 *
 * @author Andreas Kuoppa
 */
public class TicketController implements Initializable {
   
    String account = "TEST ProjectLeader";
    
    @FXML Button assignButton;
    @FXML ListView categoryList;
    private final ObservableList<String> categories = FXCollections.observableArrayList("NETWORK", "APPLICATION", "SECURITY", "USER");
    
    @FXML TextArea readCommentArea;
    @FXML TextArea writeCommentArea;
    @FXML TitledPane titledPane;
    @FXML AnchorPane anchorPane;
    @FXML TextField newTaskNameField;
    @FXML Button addTaskButton;
    
    int noOfTasks = 1;
    int maxNoOfTasks = 6;

    // Parameters for a task Row
    double origoY = 42.0;
    double textCorrectY = 14.0;
    double rowHeight = 38.0;
    
    Ticket ticket;  // The ticket on top of the pile that is going to be sent to db
    
    private IntegerProperty initTktNo = new SimpleIntegerProperty();
    // int initTktNo = 0;
    
    // Default constructor, DO NOT REMOVE!
    public TicketController() {
        
    }
    
    // Parameter constructor, runs before initialize
    // Caller should call this before initializing view
    public TicketController(int tktNo) {
        initTktNo.set(tktNo);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ----- Initializes categoryList -----
        categoryList.setItems(categories);
        categoryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Selected item: " + newValue);
                assignButton.setDisable(false);
            }
        });
        // ----- /Initializes categoryList -----
        
        // ----- Initializes the rest of the ticket data -----
        // if no-arg constructor has been called and initTktNo not set
        if (initTktNo.get() == 0) {
            ArrayList<Ticket> ticketList = loadTickets();
            ticket = ticketList.get(0);  
        }
        // If parameter constructor has been called, ticket called from double
        // click on table
        else {
            ArrayList<Ticket> ticketList = loadAssignedTickets();
            for (int x = 0; x < ticketList.size(); x++) {
                if (ticketList.get(x).getTktNo() == initTktNo.get()) {
                    ticket = ticketList.get(x);
                }
            }
            
            // Selects category
            categoryList.getSelectionModel().select(ticket.getCategory());
            
        }
        ArrayList<Comment> comments = ticket.getComments();
        setTicketName(ticket);
        setComments(comments);
        // ----- /Initializes the rest of the ticket data -----
        
        
        drawTaskUI();
    }
    
    public void setTicketName(Ticket ticket) {
        titledPane.setText(ticket.getName() + " (ticket " + ticket.getTktNo() + ")");
    }

    public void setComments(ArrayList<Comment> comments) {
        for (Comment comment : comments) {
            String previousText = readCommentArea.getText();
            
            String seperator = "¤";
            String allText = comment.getText();
            String name = allText.split(seperator)[0];
            String date = allText.split(seperator)[1];
            String commentText = allText.split(seperator)[2];
            
            readCommentArea.setText(date + ": " + name
                    + "\n"
                    + commentText
                    + "\n\n"
                    + previousText);
        }
    }
    
    // TODO: make this general in the initialize method, load tasks from db
    // At the moment, this method is only for testing purposes
    public void drawTaskUI() {
        // Generates the UI for each task row
        int rows = 1;
        for (int row = 0; row < rows; row++) {
            addRow(row, "Finish Task (default)");
        }
    }
    
    public void addRow(int row, String task) {
        Group g = new Group();
        
        CheckBox cb = new CheckBox();
        cb.setLayoutX(15.0);
        cb.setLayoutY(origoY + row * rowHeight);
        cb.setText(task);

        TextField tfBudgetH = new TextField();
        TextField tfBudgetM = new TextField();
        TextField tfActualH = new TextField();
        TextField tfActualM = new TextField();
        tfBudgetH.setLayoutX(221.0);
        tfBudgetM.setLayoutX(287.0);        
        tfActualH.setLayoutX(370.0);
        tfActualM.setLayoutX(436.0);
        tfBudgetH.setLayoutY(origoY + row * rowHeight);
        tfBudgetM.setLayoutY(origoY + row * rowHeight);
        tfActualH.setLayoutY(origoY + row * rowHeight);
        tfActualM.setLayoutY(origoY + row * rowHeight);
        tfBudgetH.setPrefWidth(40.0);
        tfBudgetM.setPrefWidth(40.0);
        tfActualH.setPrefWidth(40.0);
        tfActualM.setPrefWidth(40.0);
        
        // Limits the input of text in TextFields
        addTextLimiter(tfBudgetH, 3);
        addTextLimiter(tfActualH, 3);
        addTextLimiter(tfBudgetM, 2);
        addTextLimiter(tfActualM, 2);
        
        Button removeTaskButton = new Button();
        removeTaskButton.setLayoutX(526.0);
        removeTaskButton.setLayoutY(origoY + row * rowHeight);
        // Remove method for removeTaskButton
        removeTaskButton.setOnAction((e) -> {

            Node source = (Node) e.getSource();
            Parent parent = source.getParent();

            // Checks which row group should be removed
            int x = 0;
            int hit = 0;
            for (Node n : anchorPane.getChildren()) {
                if (n.equals(parent)) {
                    System.out.println("HIT ON " + x);
                    hit = x;
                }
                System.out.println(x);
                x++;
            }

            // Moves the row groups below the deleted one, one rowHeight up
            for (int y = hit; y < x; y++){
                Node currentGroup = (Node) anchorPane.getChildren().get(y);
                double posY = currentGroup.getLayoutY();
                currentGroup.setLayoutY(posY - rowHeight);
            }
            
            anchorPane.getChildren().remove(parent);
            addTaskButton.setDisable(false);
            newTaskNameField.setDisable(false);
        });
        Image img = new Image("/resources/minus.png");
        removeTaskButton.setGraphic(new ImageView(img));

        Text bH = new Text("h");
        Text bM = new Text("m");
        Text aH = new Text("h");
        Text aM = new Text("m");
        bH.setLayoutX(266.0);
        bM.setLayoutX(331.0);
        aH.setLayoutX(415.0);
        aM.setLayoutX(480.0);
        bH.setLayoutY(origoY + row * rowHeight + textCorrectY);
        bM.setLayoutY(origoY + row * rowHeight + textCorrectY);
        aH.setLayoutY(origoY + row * rowHeight + textCorrectY);
        aM.setLayoutY(origoY + row * rowHeight + textCorrectY);


        g.getChildren().add(cb);
        g.getChildren().add(tfBudgetH);
        g.getChildren().add(tfBudgetM);
        g.getChildren().add(tfActualH);
        g.getChildren().add(tfActualM);
        g.getChildren().add(removeTaskButton);
        g.getChildren().add(bH);
        g.getChildren().add(bM);
        g.getChildren().add(aH);
        g.getChildren().add(aM);

        anchorPane.getChildren().add(g);

        if (row >= maxNoOfTasks - 1) {
            addTaskButton.setDisable(true);
            newTaskNameField.setDisable(true);
        }
    }
    
    // TODO: work with this method
    public void addTaskHandle() {
        System.out.println("Add task clicked");
        
        int children = 0;
        for (Node n: anchorPane.getChildren()) {
            children++;
        }
        
        System.out.println("Children: " + children);
        
        addRow(children, newTaskNameField.getText());
        newTaskNameField.setText("");
        newTaskNameField.requestFocus();
    }
    
   // Handler for when assignButton is clicked
    public void assignButtonHandle() {
        double offsetX = titledPane.getWidth() * 2;
        int duration = 1500;
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), titledPane);
        
        sendTicketToDb();
        
        tt.setOnFinished((e) -> {  
            updateNewTicketsView();
        });
        tt.setByX(-offsetX);
        tt.setAutoReverse(true);
        tt.setCycleCount(1);
        tt.play();
    }
    
    // Sends the assigned ticket to database
    // TODO: continue on this method;
    public void sendTicketToDb() {
        TicketsHandler th = new TicketsHandler();
        
        // Save newComment to ticket
        String newComment = writeCommentArea.getText();
        
        Calendar today = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        String formatted = df.format(today.getTime());
        System.out.println(formatted);
        Comment commentToSend = new Comment();
        if (!newComment.equals("")) {
            commentToSend.setText(account + "¤" + formatted + "¤" + newComment);
            ticket.addComment(commentToSend);
        }
        
        // Save category to ticket
        String category = categoryList.getSelectionModel().getSelectedItem().toString();
        ticket.setCategory(category);
        
        // Set ticket status to ASSIGNED
        ticket.setStatus("ASSIGNED");
        
        // sets the tasks for the ticket
        setTasks();
        
        // Send updates to db
        th.updateTicket(ticket);
    }
    
    // Helper method for setting all tasks to ticket before sending to db
    private void setTasks() {
        // Deletes all tasks in ticket
        ticket.deleteAllTasks();
        
        // Gets all dynamically generated task nodes in anchorPane
        ObservableList<Node> nodes = anchorPane.getChildren();
        
        // For each "node row", add to task from fields accordingly,
        // and give task to ticket
        for (Node node : nodes) {
            // Cast the Node to a Group with all UI elements
            Group group = (Group) node;
            
            Task task = new Task();
            
            CheckBox cb = (CheckBox) group.getChildren().get(0);
            TextField tfBugdetH = (TextField) group.getChildren().get(1);
            TextField tfBugdetM = (TextField) group.getChildren().get(2);
            TextField tfActualH = (TextField) group.getChildren().get(3);
            TextField tfActualM = (TextField) group.getChildren().get(4);
            
            String budgetH = tfBugdetH.getText();
            String budgetM = tfBugdetM.getText();
            String actualH = tfActualH.getText();
            String actualM = tfActualM.getText();
            
            int bugdetMinutes = inMinutes(budgetH, budgetM);
            int actualMinutes = inMinutes(actualH, actualM);
            
            task.setName(cb.getText());
            task.setTimeBudgetMinutes(bugdetMinutes);
            task.setTimeSpentMinutes(actualMinutes);
            
            ticket.addTask(task);
        }
    }
    
    // Scope method that converts hours and minutes to just minutes
    private int inMinutes(String hours, String minutes) {
        int totM = 0;
        try {
            int h = Integer.parseInt(hours);
            int m = Integer.parseInt(minutes);
            totM = 60 * h + m;
        }
        catch (NumberFormatException e) {
            e.getMessage();
        }
       
        return totM;
    }
    
    // Updates the New Tickets View
    // Send ticket to db before calling this method
    public void updateNewTicketsView() {
            titledPane.setTranslateX(0);
            titledPane.toFront();
            
            // Changes back to the same window and initiates again
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("FXMLDocument.fxml"));
                Parent parent = loader.load();
                
                Scene newScene = new Scene(parent);
                
                FXMLDocumentController controller = loader.getController();
                System.out.println("blääää");
                
                //This line gets the Stage information
                // Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                Stage window = (Stage)(titledPane.getScene().getWindow());

                window.setScene(newScene);
                window.show();
                // controller.test();
                controller.movePane();
            } 
            catch (IOException ex) {
                Logger.getLogger(TicketController.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
    
    // TODO: This method should not be in this class, this is just for testing
    public ArrayList<Ticket> loadTickets() {
        TicketsHandler th = new TicketsHandler();
        th.emptyTickets();
        th.readTickets("getUnassignedTickets()");   
        return th.getTickets();
    }
    
    public ArrayList<Ticket> loadAssignedTickets() {
        TicketsHandler th = new TicketsHandler();
        th.emptyTickets();
        th.readTickets("getAssignedTickets()");   
        return th.getTickets();
    }
    
    // Adds a limit for max number of characters of a Textfield
    // Based on answer on stackoverflow
    public static void addTextLimiter(final TextField tf, final int maxLength) {
    tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
        if (tf.getText().length() > maxLength) {
            String s = tf.getText().substring(0, maxLength);
            tf.setText(s);
        }
    });
    }
}
