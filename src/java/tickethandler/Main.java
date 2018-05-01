/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tickethandler;

import javafx.scene.image.Image;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ths.Ticket;
import ths.TicketsHandler;

/**
 *
 * @author Andreas
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {       
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root, 1185, 800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/resources/ths.png"));
        stage.setTitle("Ticket Handling System");
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
