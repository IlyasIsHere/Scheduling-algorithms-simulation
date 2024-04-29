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

            Displayer.displayTable(remaining, terminated, currentTime);

            int runningTime = min(chosen.getRemainingBurstTime(), quantum);

            // Check if any process arrives while the current one is running
            for (Process p : remaining) {
                if (p.getStatus() == Status.NOT_ARRIVED_YET && p.getArrivalTime() <= currentTime + runningTime) {
                    p.setStatus(Status.READY);
                    ready.add(p);
                    Displayer.displayTable(remaining, terminated, p.getArrivalTime());
                }
            }

            // Advance time by the minimum between the remaining burst time, and the quantum
            currentTime += runningTime;
            chosen.setRemainingBurstTime(chosen.getRemainingBurstTime() - runningTime);

            if (chosen.getRemainingBurstTime() == 0) { // If it terminates
                chosen.setStatus(Status.TERMINATED);
                chosen.setTerminationTime(currentTime);

                // Waiting time is turnaroundTime - burstTime
                chosen.setWaitingTime(chosen.calcTurnaroundTime() - chosen.getBurstTime());

                remaining.remove(chosen);
                terminated.add(chosen);
                Displayer.displayTable(remaining, terminated, currentTime);
            } else {
                // Add it back to the rear of the queue
                ready.add(chosen);
                chosen.setStatus(Status.READY);
            }
        }

        // Finally, display performance metrics
        Displayer.displayPerformanceMetrics(terminated);
    }
}
