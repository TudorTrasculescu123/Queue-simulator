package com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model;

public class Task {
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public synchronized int getArrivalTime() {
        return arrivalTime;
    }

    public synchronized int getID() {
        return ID;
    }

    public synchronized int getServiceTime() {
        return serviceTime;
    }

    public synchronized void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
}
