package com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.BussinessLogic;

import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Controller.Controller;
import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Task;
import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Server;
import javafx.application.Platform;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationManager implements Runnable{
    private int peakHour;
    private int maxPeopleInQueues;
//    private FileWriter fileWriter;
    private double averageWaitingTime;
    private double averageServiceTime;
    private Controller controller;
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int numberOfServers;
    public int numberOfClients;
    public int minArrivalTime;
    public int maxArrivalTime;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private List<Task> generatedTasks;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int numberOfServers, int numberOfClients, int minArrivalTime, int maxArrivalTime){
        averageServiceTime = 0;
        averageWaitingTime = 0;
        peakHour = 0;
        maxPeopleInQueues = 0;
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        selectionPolicy = SelectionPolicy.SHORTEST_TIME;
        generatedTasks = Collections.synchronizedList(new ArrayList<Task>());
        generateRandomTasks();
        scheduler = new Scheduler(numberOfServers, 100);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

//    public void setFileWriter(FileWriter fileWriter) {
//        this.fileWriter = fileWriter;
//    }

    private void generateRandomTasks() {
        Random random = new Random();
        for(int i = 0; i < numberOfClients; i++){
            int processingTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime  ;
            int arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            Task task = new Task(i+1, arrivalTime, processingTime);
            generatedTasks.add(task);
      }

        Collections.sort(generatedTasks, new Comparator<Task>(){
            public int compare(Task t1, Task t2){
                return t1.getArrivalTime() - t2.getArrivalTime();
            }
        });
    }

    @Override
    public void run(){
        int currentTime = 0;
        while(currentTime <= timeLimit){
            List<Task> toRemove = Collections.synchronizedList(new ArrayList<Task>());
            for(Task task: generatedTasks){
                if(task.getArrivalTime() == currentTime){
                    Server bestServer = scheduler.dispatchTask(task);
                    toRemove.add(task);
//                    AtomicInteger waitingPeriod = bestServer.getWaitingPeriod();
//                    averageWaitingTime +=  waitingPeriod.get();
                    averageServiceTime += task.getServiceTime();
//                    synchronized (waitingPeriod){
//                        try {
//                            waitingPeriod.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        //System.out.println("Server in Simulation: "+ bestServer.getServerID());
//                    }
                }
            }
            generatedTasks.removeAll(toRemove);
            String s = "";
            s = s + "Time " + currentTime + "\n" + "Waiting clients: ";
            for(Task task: generatedTasks){
                s = s + "(" + task.getID() + "," + task.getArrivalTime() + "," + task.getServiceTime() + "); ";
            }
            s = s + "\n";
            List<Server> servers = scheduler.getServers();
            int nrPeople = 0;
            for(Server server: servers){
                averageWaitingTime += server.getWaitingPeriod().get();
                s = s + "Queue " + server.getServerID() + ": ";
                LinkedBlockingQueue<Task> tasks = (LinkedBlockingQueue<Task>) server.getTasks();
                for(Task task: tasks){
                    nrPeople ++ ;
                    s = s + "(" + task.getID() + "," + task.getArrivalTime() + "," + task.getServiceTime() + "); ";
                }
                s = s + "\n";
            }
            if(nrPeople > maxPeopleInQueues){
                maxPeopleInQueues = nrPeople;
                peakHour = currentTime;
            }
            s = s + "\n";
            writeResults(s);
//            try {
//                fileWriter.write(s);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            System.out.println();
            currentTime ++;
            if(checkEnd(servers, generatedTasks) == 1){
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(currentTime != 0)
            averageWaitingTime = averageWaitingTime / currentTime;
        interruptThreads();
    }

    public void writeResults(String s){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.setText(s);
            }
        });
    }

    public int checkEnd(List<Server> servers, List<Task> generatedTasks){
        if(generatedTasks.isEmpty()){
            for(Server server: servers){
                if(!server.getTasks().isEmpty()){
                    return 0;
                }
            }
            return 1;
        }
        return 0;
    }

    public void interruptThreads(){
        averageWaitingTime = 1.0 * averageWaitingTime / numberOfServers;
        averageServiceTime = 1.0 * averageServiceTime / numberOfClients;
        String s = "Average waiting time for all queues: " + averageWaitingTime + "\n";
        s = s + "Average service time for all queues: " + averageServiceTime + "\n";
        s = s + "Peak hour: " + peakHour + "\n" ;
        writeResults(s);
//        try {
//            fileWriter.write(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        List<Server> servers = scheduler.getServers();
        for(Server server: servers){
            server.changeRunningState(false);
        }
    }
}
