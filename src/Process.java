enum Status {
    READY,
    NOT_ARRIVED_YET,
    TERMINATED,
    RUNNING
}

public class Process {
    private int id;
    private int arrivalTime;
    private int burstTime;
    private int waitingTime;
    private int priority;
    private Status status;
    private int terminationTime;
    private int remainingBurstTime;

    public int calcTurnaroundTime() {
        return terminationTime - arrivalTime;
    }

    public void reset() {
        remainingBurstTime = burstTime;
        status = Status.NOT_ARRIVED_YET;
        waitingTime = 0;
    }

    public int getTerminationTime() {
        return terminationTime;
    }

    public void setTerminationTime(int terminationTime) {
        this.terminationTime = terminationTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Process(int id, int arrivalTime, int burstTime, int priority) {
        if (id < 0) throw new IllegalArgumentException("ID must be a positive integer");
        if (arrivalTime < 0) throw new IllegalArgumentException("Arrival time must be a positive integer");
        if (burstTime < 0) throw new IllegalArgumentException("Burst time must be a positive integer");

        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.status = Status.NOT_ARRIVED_YET;
        this.remainingBurstTime = burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
