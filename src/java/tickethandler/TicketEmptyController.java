/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickethandler;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.util.Duration;

/**
 *
 * @author Andreas Kuoppa
 */
public class TicketEmptyController {
   
    @FXML TitledPane titledPane;
    double offsetY = 9.0;
    int duration = 250;
    
    // When entering over a ticket in the pile
    public void onEnter(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), titledPane);
        tt.setByY(-offsetY);
        tt.play();
    }
    
    // When exiting hovering over a ticket in the pile
    public void onExit() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), titledPane);
        tt.setToY(0);
        tt.play();       
    }
}