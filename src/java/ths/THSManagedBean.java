/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ths;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Björn
 */
@ManagedBean
@RequestScoped
public class THSManagedBean {

    /**
     * Creates a new instance of THSManagedBean
     */
    public THSManagedBean() {
        TicketsHandler th = new TicketsHandler();
        th.readTickets("getAllTickets()");
        ArrayList<Ticket> tickets = th.getTickets();
    }

    
}
