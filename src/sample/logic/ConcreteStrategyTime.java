package sample.logic;

import sample.dataModels.Server;
import sample.dataModels.Task;

import java.util.ArrayList;

public class ConcreteStrategyTime implements Strategy {

    public void addTask(ArrayList<Server> servers, Task t) {

        int min = servers.get(0).getWaitingPeriod().get();
        int iMin = 0;

        for (int i=1; i<servers.size(); i++) {

            if(min > servers.get(i).getWaitingPeriod().get()) {

                min = servers.get(i).getWaitingPeriod().get();
                iMin = i;
            }
        }
        servers.get(iMin).addTask(t);
    }
}
