package sample.dataModels;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private LinkedBlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server() {

        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
    }

    public AtomicInteger getWaitingPeriod() {

        return waitingPeriod;
    }

    public LinkedBlockingQueue<Task> getTasks() {

        return tasks;
    }

    public void addTask(Task newTask) {

        this.waitingPeriod.addAndGet(newTask.getProcessingTime());
        this.tasks.add(newTask);
    }

    public String print() {

        String s = new String();
        Object[] t = tasks.toArray();

        for(int i=0; i<t.length; i++)
            s = s + ((Task)t[i]).print() + " ";

        return s;
    }

    public void run() {

        Task t = null;
        while(true) {

            if (t == null) {

                t = tasks.peek();
            }
            else {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.waitingPeriod.decrementAndGet();
                t.decrementProcessingTime();
                if (t.getProcessingTime() == 0) {

                    this.tasks.poll();
                    t = null;
                }
            }
        }
    }

}
