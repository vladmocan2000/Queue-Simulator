package sample.dataModels;

public class Task {

    private int id;
    private int arrivalTime;
    private int processingTime;

    public Task(int id, int arrivalTime, int processingTime) {

        this.id = id;
        this.arrivalTime = arrivalTime;
        this.processingTime  = processingTime;
    }

    public int getArrivalTime() {

        return this.arrivalTime;
    }

    public int getProcessingTime() {

        return this.processingTime;
    }

    public void decrementProcessingTime() {

        this.processingTime--;
    }

    public int getId() {

        return this.id;
    }

    public String print() {

        String s = new String();
        s = "(" + this.id + "," + this.arrivalTime + "," + this.processingTime + ")";

        return s;
    }
}
