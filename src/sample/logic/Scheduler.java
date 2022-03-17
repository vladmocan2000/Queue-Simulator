package sample.logic;

import sample.dataModels.Server;
import sample.dataModels.Task;

import java.util.ArrayList;

public class Scheduler {

    private ArrayList<Server> servers;
    private int maxNoServers;
    private Strategy strategy;

    public Scheduler(int maxNoServers, SelectionPolicy selectionPolicy) {

        this.servers = new ArrayList<Server>();
        this.maxNoServers = maxNoServers;
        changeStrategy(selectionPolicy);

        for (int i=0; i<this.maxNoServers; i++) {

            Server s = new Server();
            servers.add(s);
            Thread server = new Thread(s);
            server.setDaemon(true);
            server.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {

        if(policy == SelectionPolicy.SHORTEST_TIME)
            this.strategy = new ConcreteStrategyTime();
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
            this.strategy = new ConcreteStrategyClients();
    }

    public void dispatchTask(Task t) {

        strategy.addTask(servers, t);
    }

    public ArrayList<Server> getServers() {

        return servers;
    }
}
