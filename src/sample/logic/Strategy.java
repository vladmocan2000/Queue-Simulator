package sample.logic;

import sample.dataModels.Server;
import sample.dataModels.Task;

import java.util.ArrayList;
import java.util.List;

public interface Strategy {

    public void addTask(ArrayList<Server> servers, Task t);
}
