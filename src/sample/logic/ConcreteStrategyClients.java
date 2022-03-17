package sample.logic;

import sample.dataModels.Server;
import sample.dataModels.Task;

import java.util.ArrayList;

public class ConcreteStrategyClients implements Strategy{

    public void addTask(ArrayList<Server> servers, Task t) {

        int min = servers.get(0).getTasks().size();
        int iMin = 0;

        for (int i=1; i<servers.size(); i++) {

            if(min > servers.get(i).getTasks().size()) {

                min = servers.get(i).getTasks().size();
                iMin = i;
            }
        }

        servers.get(iMin).addTask(t);
    }
}
