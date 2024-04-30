import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.min;

public class RoundRobin extends Scheduler {

    int quantum;

    public RoundRobin(int quantum) {
        this.quantum = quantum;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void simulate(ArrayList<Process> processes) throws InterruptedException {

        // Keeping track of the remaining, ready, and terminated processes
        ArrayList<Process> remaining = new ArrayList<>(processes);
        remaining.sort(Comparator.comparingInt(Process::getArrivalTime));
        Queue<Process> ready = new LinkedList<>();
        ArrayList<Process> terminated = new ArrayList<>();

        int currentTime = 0;

        Displayer.displayTable(processes, currentTime);
        // Run the loop until there is no remaining process (all terminated)
        while (!remaining.isEmpty()) {

            // Adding the newly arrived processes to the ready queue
            for (Process p : remaining) {
                if (p.getStatus() == Status.NOT_ARRIVED_YET && p.getArrivalTime() <= currentTime) {
                    p.setStatus(Status.READY);

                    Displayer.displayTable(remaining, terminated, p.getArrivalTime());
                    ready.add(p);
                }
            }

            // If no process has arrived yet, move currentTime to minimum arrival time of the remaining processes, then continue
            if (ready.isEmpty()) {
                currentTime = getMinArrivalTime(remaining);
                continue;
            }

            // Choosing the process to run now (the front of the queue). We remove it from the ready queue.
            Process chosen = ready.poll();

            // Running it for a quantum of time
            chosen.setStatus(Status.RUNNING);
            if (chosen.getStartingTime() == -1) {
                chosen.setStartingTime(currentTime);
            }

            Displayer.displayTable(remaining, terminated, currentTime);

            // The running time is the minimum between the remaining burst time, and the quantum
            int runningTime = min(chosen.getRemainingBurstTime(), quantum);
            int startingTime = currentTime;
            int endingTime = startingTime + runningTime;

            // Check if any process arrives while the current one is running
            for (Process p : remaining) {
                if (p.getStatus() == Status.NOT_ARRIVED_YET && p.getArrivalTime() <= endingTime) {
                    p.setStatus(Status.READY);
                    ready.add(p);

                    chosen.setRemainingBurstTime(chosen.getRemainingBurstTime() - (p.getArrivalTime() - currentTime));
                    currentTime = p.getArrivalTime();

                    Displayer.displayTable(remaining, terminated, p.getArrivalTime());
                }
            }


            chosen.setRemainingBurstTime(chosen.getRemainingBurstTime() - (endingTime - currentTime));
            currentTime = endingTime;

            if (chosen.getRemainingBurstTime() == 0) { // If it terminates
                chosen.setStatus(Status.TERMINATED);
                chosen.setTerminationTime(currentTime);

                // Waiting time is turnaroundTime - burstTime
                chosen.setWaitingTime(chosen.calcTurnaroundTime() - chosen.getBurstTime());

                remaining.remove(chosen);
                terminated.add(chosen);
            } else {
                // Add it back to the rear of the queue
                ready.add(chosen);
                chosen.setStatus(Status.READY);
            }
            Displayer.displayTable(remaining, terminated, currentTime);
        }

        // Finally, display performance metrics
        Displayer.displayPerformanceMetrics(terminated);
    }
}
