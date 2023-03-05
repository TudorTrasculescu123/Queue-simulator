package com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model;

import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int serverID;
    private boolean isRunning = true;

    public Server(int serverID){
        this.serverID = serverID;
        tasks = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger();
        this.waitingPeriod.set(0);
    }

    public void changeRunningState(boolean isRunning){
        this.isRunning = isRunning;
    }

    public void addTask(Task newTask){
        try {
            tasks.put(newTask);
            this.waitingPeriod.getAndAdd(newTask.getServiceTime());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(isRunning){
            if(!tasks.isEmpty()){
                try {
                    Task currentTask = tasks.peek();
                    if(currentTask != null) {
                        int n = currentTask.getServiceTime();
                        for (int i = 0; i < n; i++) {
//                            synchronized (waitingPeriod) {
//                                waitingPeriod.notify();
//                            }
                            Thread.sleep(1000);
                            waitingPeriod.getAndDecrement();
                            currentTask.setServiceTime(currentTask.getServiceTime() - 1);

                        }
                        tasks.take();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getServerID() {
        return serverID;
    }

    public BlockingQueue<Task> getTasks(){
        return this.tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
}
