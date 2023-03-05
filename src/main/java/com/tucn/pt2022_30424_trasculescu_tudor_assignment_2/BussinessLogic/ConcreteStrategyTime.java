package com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.BussinessLogic;

import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Server;
import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public Server addTask(List<Server> servers, Task t){
        Server bestServer = null;
        int minim = 100000;
        for(Server server : servers){
            if(minim > server.getWaitingPeriod().get()){
                minim = server.getWaitingPeriod().get();
                bestServer = server;
            }
        }
        if(bestServer != null){
//            System.out.print(minim + "  dispatched: ");
//            System.out.print("(" + t.getID() + "," + t.getArrivalTime() + "," + t.getServiceTime() + "); ");
//            System.out.println("to: " + bestServer.getServerID());
            bestServer.addTask(t);
        }
        return bestServer;
    }
}
