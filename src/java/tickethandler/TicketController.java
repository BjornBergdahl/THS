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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import ths.Comment;
import ths.Ticket;
import ths.TicketsHandler;

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
        
        // ----- Loads data from database and initializes comments -----
        ArrayList<Ticket> ticketList = loadTickets();
        ticket = ticketList.get(0);
        ArrayList<Comment> comments = ticket.getComments();
        setTicketName(ticket);
        setComments(comments);
        // ----- /Loads data from database and initializes comments -----
        
        drawTaskUI();
    }
    
    public void setTicketName(Ticket ticket) {
        titledPane.setText(ticket.getName() + " (ticket " + ticket.getTktNo() + ")");
    }
    
    public void setCategories(ObservableList<String> categories) {
        categoryList.setItems(categories);
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
        System.out.println("Assign clicked");
        setTasks();
        
        // TODO: UNCOMMENT THIS!
        
        /*
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
        tt.play();*/
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
        
        if (!newComment.equals("")) {
            ticket.addComment(account + "¤" + formatted + "¤" + newComment);
        }
        
        // Save category to ticket
        String category = categoryList.getSelectionModel().getSelectedItem().toString();
        ticket.setCategory(category);
        
        // Set ticket status to ASSIGNED
        ticket.setStatus("ASSIGNED");
        
        // Send updates to db
        th.updateTicket(ticket);
    }
    
    private void setTasks() {
        // Gets all dynamically generated task nodes in anchorPane
        ObservableList<Node> nodes = anchorPane.getChildren();
        for (Node node : nodes) {
            Group group = (Group) node;
            TextField tf = (TextField) group.getChildren().get(1);
            String text = tf.getText();
            System.out.println(text);
        }
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
