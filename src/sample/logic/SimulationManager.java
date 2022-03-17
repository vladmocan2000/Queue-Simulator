package sample.logic;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.dataModels.Task;
import sample.GUI.Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

class SortByArrivalTime implements Comparator<Task>
{
    public int compare(Task a, Task b) {

        return a.getArrivalTime() - b.getArrivalTime();
    }
}

public class SimulationManager implements Runnable {

    private int numberOfClients;
    private int numberOfServers;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minProcessingTime;
    private int maxProcessingTime;
    private int timeLimit;
    private SelectionPolicy selectionPolicy;

    private LinkedBlockingQueue<Task> tasks;
    private Scheduler scheduler;

    TextArea queues;

    public SimulationManager(int numberOfClients, int numberOfServers, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime, int timeLimit, SelectionPolicy selectionPolicy, TextArea queues) {

        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.timeLimit = timeLimit;
        this.selectionPolicy = selectionPolicy;
        this.queues = queues;
    }

    private void generateNRandomTasks() {

        tasks = new LinkedBlockingQueue<Task>();
        Random rand = new Random();

        ArrayList<Task> createTasks = new ArrayList<Task>();

        for(int i=0; i<numberOfClients; i++) {

            int processingTime = minProcessingTime + rand.nextInt(maxProcessingTime - minProcessingTime + 1);
            int arrivalTime = minArrivalTime + rand.nextInt(maxArrivalTime - minArrivalTime + 1);
            Task t = new Task(i+1, arrivalTime, processingTime);

            createTasks.add(t);
        }

        Collections.sort(createTasks, new SortByArrivalTime());
        for (Task t : createTasks) {

            tasks.add(t);
        }

    }

    public void print(int currentTime) throws IOException {

        String out = new String();

        String s = new String();
        Object[] t = tasks.toArray();
        for(int i=0; i<t.length; i++)
            s = s + ((Task)t[i]).print() + " ";

        out = "Time " + currentTime + "\n";
        out = out + "Waiting clients: ";
        out = out + s + "\n";

        for(int i=0; i<this.scheduler.getServers().size(); i++) {

            int index = i+1;
            out = out + "Queue " + index + ": " + this.scheduler.getServers().get(i).print() + "\n";
        }
        out = out + "\n";

        queues.setText(out);

        usingPrintWriter(out);
    }

    public static void truncateFile() throws IOException
    {
        FileWriter fileWriter = new FileWriter("a.txt", false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("Queues Simulator: \n\n");
        printWriter.close();
    }

    public static void usingPrintWriter(String textToAppend) throws IOException
    {
        FileWriter fileWriter = new FileWriter("a.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(textToAppend);
        printWriter.close();
    }

    public void run() {

        try {
            truncateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int currentTime = 0;
        generateNRandomTasks();
        scheduler = new Scheduler(this.numberOfServers, selectionPolicy);
        while (currentTime <= timeLimit) {

            int i = 0;
            Task t = tasks.peek();
            while (t != null && t.getArrivalTime() == currentTime) {

                scheduler.dispatchTask(t);
                tasks.poll();
                t = tasks.peek();
            }

            try {
                this.print(currentTime);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentTime++;
        }
    }

}