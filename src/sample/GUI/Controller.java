package sample.GUI;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.logic.SelectionPolicy;
import sample.logic.SimulationManager;

public class Controller {

    public TextArea queues;
    ObservableList<String> choiseList = FXCollections.observableArrayList("SHORTEST_TIME", "SHORTEST_QUEUE");

    public Button button3;
    public TextField numberOfServers;
    public TextField numberOfClients;
    public TextField minArrivalTime;
    public TextField maxArrivalTime;
    public TextField minProcessingTime;
    public TextField maxProcessingTime;
    public TextField timeLimit;
    public ChoiceBox chooseStrategy;

    public void initialize() {

        chooseStrategy.setItems(choiseList);
    }

    public void start() {


        if(chooseStrategy.getSelectionModel().getSelectedItem() == choiseList.get(0)) {

            SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
            SimulationManager s = new SimulationManager(Integer.parseInt(numberOfClients.getText()), Integer.parseInt(numberOfServers.getText()), Integer.parseInt(minArrivalTime.getText()), Integer.parseInt(maxArrivalTime.getText()), Integer.parseInt(minProcessingTime.getText()), Integer.parseInt(maxProcessingTime.getText()), Integer.parseInt(timeLimit.getText()), selectionPolicy, queues);
            new Thread(s).start();
        }
        else {

            SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
            SimulationManager s = new SimulationManager(Integer.parseInt(numberOfClients.getText()), Integer.parseInt(numberOfServers.getText()), Integer.parseInt(minArrivalTime.getText()), Integer.parseInt(maxArrivalTime.getText()), Integer.parseInt(minProcessingTime.getText()), Integer.parseInt(maxProcessingTime.getText()), Integer.parseInt(timeLimit.getText()), selectionPolicy, queues);
            new Thread(s).start();
        }
    }
}
