package com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.BussinessLogic;

import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Task;
import com.tucn.pt2022_30424_trasculescu_tudor_assignment_2.Model.Server;

import java.util.List;

public interface Strategy {
    public Server addTask(List<Server> servers, Task t);
}
