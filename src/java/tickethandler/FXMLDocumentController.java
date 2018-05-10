/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickethandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import ths.Comment;
import ths.Ticket;
import ths.TicketsHandler;

/**
 *
 * @author Andreas Kuoppa
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private Window dialog;
    @FXML private TicketController ticketController;
    
    @FXML private AnchorPane ticketPane;
    @FXML private ListView categoryListView;
    
    // FXML tags for accessing the Ticket TitledPanes
    @FXML private TitledPane ticket;
    
    private ArrayList<Ticket> tickets = new ArrayList<>();;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawPileTickets();
    }
    
    // Draws the tickets in the pile, i.e. all but the front ticket.
    public void drawPileTickets() {
        
        tickets.clear();
        tickets = loadTickets();                     // Loads tickets from db
        tickets.remove(0);                           // Removes the first entry, this is the active ticket, handled differently
        Collections.reverse(tickets);                // Reverses the list, as it's easiest if the tickets are drawn back to front
        
        double paddingX = 15.0;
        double paddingY = 15.0;
        
        int noOfTickets = tickets.size();
        double x = ticket.getLayoutX() + paddingX * (noOfTickets + 1);
        double y = ticket.getLayoutY() - paddingY * (noOfTickets + 1);
        for (Ticket t:tickets) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            try {
                TitledPane p = fxmlLoader.load(getClass().getResource("Ticket_empty.fxml").openStream());
                x -= paddingX;
                y += paddingY;
                p.setLayoutX(x);
                p.setLayoutY(y);
                p.setText(t.getName() + " (ticket " + t.getTktNo() + ")");  // preferably this would be done in a custom controller
                ticketPane.getChildren().add(p);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ticket.toFront();
    }
    
    public void movePane() {
        double offsetX = 15.0;
        double offsetY = 15.0;
        int duration = 500;
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), ticketPane);
        tt.setByX(-offsetX);
        tt.setByY(offsetY);
        tt.setAutoReverse(true);
        tt.setCycleCount(1);
        tt.play();
    }
    
    public ArrayList<Ticket> loadTickets() {
        TicketsHandler th = new TicketsHandler();
        return th.getTickets();
    }
    
    
}
