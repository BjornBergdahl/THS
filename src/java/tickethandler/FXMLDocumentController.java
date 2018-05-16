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
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import TicketModel.Ticket;
import TicketModel.TicketsHandler;

/**
 *
 * @author Andreas Kuoppa
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML private TableView<Ticket> table;
    @FXML private AnchorPane ticketPane;
    @FXML private TitledPane ticket;
    
    private ArrayList<Ticket> tickets = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawPileTickets();
        updateTable();      // TODO: 
        
        // Sets a doubleclick eventhandler for a row of the table
        table.setRowFactory( tv -> {
        TableRow<Ticket> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Ticket ticket = row.getItem();
                    System.out.println("Doubleclick detected, row data: " + ticket);
                    int tktNo = ticket.getTktNo();
                    openTicket(tktNo);
                }
            });
            return row ;
        });
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
    
    // TODO: This method should probably be a void that updates the table
    // When the underlying db is updated, by an observable pattern.
    private void updateTable (){
        
        TicketsHandler th = new TicketsHandler();
        th.emptyTickets();
        th.readTickets("getAssignedTickets()");   
        ObservableList assignedTickets = FXCollections.observableArrayList(th.getTickets());
        
        // table.clear();
        table.getItems().setAll(assignedTickets);
        table.refresh();
        
        System.out.println("Table loaded with data");
    }
    
    // Opens a ticket with a specific ticket number in a new window
    private void openTicket (int ticketNumber){
        
        try {
            TicketController tc = new TicketController(ticketNumber);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ticket_1.fxml"));
            loader.setController(tc);
            Stage stage = new Stage();
            Scene scene = new Scene((TitledPane) loader.load(), 575, 380);
            stage.setScene(scene);
            
            stage.setResizable(false);
            stage.getIcons().add(new Image("/resources/ths.png"));
            stage.setTitle("Ticket Handling System");
            
            stage.show();
        }
        catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
