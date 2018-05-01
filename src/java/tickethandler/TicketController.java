/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickethandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
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
   
    @FXML ListView categoryList;
    private final ObservableList<String> categories = FXCollections.observableArrayList("Network", "Application", "Security", "User");
    
    @FXML TextArea readCommentArea;
    @FXML TitledPane titledPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryList.setItems(categories);
        // readCommentArea.setText("test");
        
        // -----TEST-----
        ArrayList<Ticket> ticketList = loadTickets();
        Ticket ticket = ticketList.get(0);
        ArrayList<Comment> comments = ticket.getComments();
        setTicketName(ticket);
        setComments(comments);
        // -----TEST-----
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
    
    
   // Handler for when assignButton is clicked
    public void assignButtonHandle() {
        double offsetX = titledPane.getWidth() * 2;
        int duration = 1500;
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), titledPane);
        
        tt.setOnFinished((ActionEvent event) -> {
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
        });
        tt.setByX(-offsetX);
        tt.setAutoReverse(true);
        tt.setCycleCount(1);
        tt.play();
    }
    
    // TODO: This method should not be in this class, this is just for testing
    public ArrayList<Ticket> loadTickets() {
        TicketsHandler th = new TicketsHandler();
        th.emptyTickets();
        th.readTickets("getUnassignedTickets()");   
        return th.getTickets();
    }
}
