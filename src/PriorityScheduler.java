import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

// This class implements the priority scheduling algorithm (non-preemptive). The lower the priority number, the higher the priority of the process.
public class PriorityScheduler extends Scheduler {

    public void simulate(ArrayList<Process> processes) throws InterruptedException {

        // Keeping track of the remaining, ready, and terminated processes
        ArrayList<Process> remaining = new ArrayList<>(processes);
        remaining.sort(Comparator.comparingInt(Process::getArrivalTime));
        ArrayList<Process> ready = new ArrayList<>();
        ArrayList<Process> terminated = new ArrayList<>();

        int currentTime = 0;

        // Run the loop until there is no remaining process (all terminated)
        while (!remaining.isEmpty()) {

            // Adding the newly arrived processes to the ready array
            for (Process p: remaining) {
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


            // Choosing the process to run now (with the highest priority, or if there are equal-priority processes, get the first arrived one (FCFS))
            Process chosen = getChosen(ready);

            // Removing the picked process from the ready queue, and running it
            ready.remove(chosen);
            chosen.setStatus(Status.RUNNING);
            chosen.setWaitingTime(currentTime - chosen.getArrivalTime());
            Displayer.displayTable(remaining, terminated, currentTime);

            // Check if any process arrives while the current one is running
            for (Process p: remaining) {
                if (p.getStatus() == Status.NOT_ARRIVED_YET && p.getArrivalTime() < currentTime + chosen.getBurstTime()) {
                    p.setStatus(Status.READY);
                    ready.add(p);
                    Displayer.displayTable(remaining, terminated, p.getArrivalTime());
                }
            }

            // Chosen process terminates
            currentTime += chosen.getBurstTime();
            chosen.setStatus(Status.TERMINATED);
            chosen.setTerminationTime(currentTime);
            remaining.remove(chosen);
            terminated.add(chosen);
            Displayer.displayTable(remaining, terminated, currentTime);
        }

        // Finally, display performance metrics
        Displayer.displayPerformanceMetrics(terminated);
    }

    /**
     *
     * @param ready The ready processes
     * @return The process to run now (the one with the highest priority, or if there are equal-priority processes, the first arrived one (FCFS))
     */
    public static Process getChosen(ArrayList<Process> ready) {
        Process chosen = ready.get(0);
        for (Process p: ready) {
            if (p.getPriority() < chosen.getPriority()) {
                chosen = p;
            } else if (p.getPriority() == chosen.getPriority()) {
                if (p.getArrivalTime() < chosen.getArrivalTime()) {
                    chosen = p;
                }
            }
        }
        return chosen;
    }
}
