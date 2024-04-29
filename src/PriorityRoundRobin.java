import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PriorityRoundRobin extends Scheduler {
    private int quantum;
    private ArrayList<Queue<Process>> queues;

    public PriorityRoundRobin(int quantum, int numPriorities) {
        this.quantum = quantum;
        this.queues = new ArrayList<>(numPriorities);
        for (int i = 0; i < numPriorities; i++) {
            queues.add(new LinkedList<>());
        }
    }

    public void simulate(ArrayList<Process> processes) throws InterruptedException {
        // Keeping track of the remaining and terminated processes
        ArrayList<Process> remaining = new ArrayList<>(processes);
        ArrayList<Process> terminated = new ArrayList<>();

        int currentTime = 0;

        Displayer.displayTable(processes, currentTime);

        // Run the loop until there is no remaining process (all terminated)
        while (!remaining.isEmpty()) {
            // Add newly arrived processes to their respective priority queues
            for (Process p : remaining) {
                if (p.getStatus() == Status.NOT_ARRIVED_YET && p.getArrivalTime() <= currentTime) {
                    p.setStatus(Status.READY);
                    int priority = p.getPriority();

                    // Add it to its corresponding queue
                    queues.get(priority).offer(p);
                    Displayer.displayTable(remaining, terminated, p.getArrivalTime());
                }
            }

            // If no process has arrived yet, move currentTime to minimum arrival time of the remaining processes, then continue
            if (allQueuesEmpty()) {
                currentTime = getMinArrivalTime(remaining);
                continue;
            }

            // Find the index of the highest priority non-empty queue
            int highestPriorityQueue = findHighestPriorityQueue();

            // Choose the process to run now (the front of the highest priority queue)
            Process chosen = queues.get(highestPriorityQueue).poll();

            // Run the chosen process for a quantum of time
            chosen.setStatus(Status.RUNNING);

            // The running time is the minimum between the remaining burst time, and the quantum
            int runningTime = Math.min(chosen.getRemainingBurstTime(), quantum);
            int startingTime = currentTime;
            int endingTime = startingTime + runningTime;

            Displayer.displayTable(remaining, terminated, currentTime);

            // Check if any process arrives while the current one is running
            for (Process p : remaining) {
                if (p.getStatus() == Status.NOT_ARRIVED_YET && p.getArrivalTime() <= endingTime) {
                    p.setStatus(Status.READY);
                    int priority = p.getPriority();
                    queues.get(priority).offer(p);

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
                chosen.setWaitingTime(chosen.calcTurnaroundTime() - chosen.getBurstTime());
                remaining.remove(chosen);
                terminated.add(chosen);
            } else {
                // Add it back to the rear of its priority queue
                int priority = chosen.getPriority();
                queues.get(priority).offer(chosen);
                chosen.setStatus(Status.READY);
            }
            Displayer.displayTable(remaining, terminated, currentTime);

        }

        // Finally, display performance metrics
        Displayer.displayPerformanceMetrics(terminated);
    }

    private boolean allQueuesEmpty() {
        for (Queue<Process> queue : queues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the highest priority non-empty queue
     * @return The index of the highest priority non-empty queue in the queues ArrayList
     */
    private int findHighestPriorityQueue() {
        for (int i = 0; i < queues.size(); i++) {
            if (!queues.get(i).isEmpty()) {
                return i;
            }
        }
        return -1; // This should never happen
    }

}