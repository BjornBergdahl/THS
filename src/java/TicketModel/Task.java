/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicketModel;

/**
 *
 * @author Björn
 */
public class Task {

    private int timeBudgetMinutes;
    private int timeSpentMinutes;
    
    private int taskNo;
    private int tktNoTsk;
    private String name;
    

    public void setTimeSpentMinutes(int timeSpentMinutes) {
        this.timeSpentMinutes = timeSpentMinutes;
    }

    public void setTimeBudgetMinutes(int timeBudgetMinutes) {
        this.timeBudgetMinutes = timeBudgetMinutes;
    }

    public void setTaskNo(int taskNo) {
        this.taskNo = taskNo;
    }

    public void setTktNo(int tktNoTsk) {
        this.tktNoTsk= tktNoTsk;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeBudgetMinutes() {
        return timeBudgetMinutes;
    }

    public int getTimeSpentMinutes() {
        return timeSpentMinutes;
    }

    public int getTaskNo() {
        return taskNo;
    }

    public int getTktNoTsk() {
        return tktNoTsk;
    }

    public String getName() {
        return name;
    }
    
}
