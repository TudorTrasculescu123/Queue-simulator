package com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.BussinessLogic;

import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Server;
import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer){
        servers = Collections.synchronizedList(new ArrayList<Server>());
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        changeStrategy(SelectionPolicy.SHORTEST_TIME);
        for(int i = 0; i < maxNoServers; i++){
            Server server = new Server(i+1);
            Thread thread = new Thread(server);
            servers.add(server);
            thread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyTime();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }

    public Server dispatchTask(Task t){
        return strategy.addTask(this.servers, t);
    }

    public List<Server> getServers(){
        return servers;
    }
}
