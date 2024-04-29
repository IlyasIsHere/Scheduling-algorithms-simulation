import java.util.ArrayList;
import java.util.Comparator;
import static java.lang.Thread.sleep;

// This class implements the First-Come-First-Served (FCFS) scheduling algorithm
public class FCFS extends Scheduler {

    /**
     * This method simulates the First-Come-First-Served (FCFS) scheduling algorithm, and displays a new table after an action happens (process terminates, process arrives, or process starts running)
     * @param processes
     * @throws InterruptedException
     */
    public void simulate(ArrayList<Process> processes) throws InterruptedException {

        // Sorting the processes based on first arrival time
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int n = processes.size();

        int currentTime = 0;

        Displayer.displayTable(processes, currentTime);
        for (int i = 0; i < n; i++) {
            Process p = processes.get(i);
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
                p.setStatus(Status.READY);
                // (process just arrived)
                Displayer.updateTable(processes, currentTime);
            }

            p.setStatus(Status.RUNNING);

            // Waiting time of process p is the difference of when it first arrived, and now (because at the current time, process p started actually running)
            p.setWaitingTime(currentTime - p.getArrivalTime());

            // (process started running)
            Displayer.updateTable(processes, currentTime);
            int startingTime = currentTime;

            // Updating other processes (the ones who arrived while process p was running) and showing when each one arrives
            for (int j = i + 1; j < n; j++) {
                Process p2 = processes.get(j);
                if (p2.getArrivalTime() <= startingTime + p.getBurstTime() && p2.getStatus() == Status.NOT_ARRIVED_YET) {
                    // (process arrives)
                    p2.setStatus(Status.READY);

                    p.setRemainingBurstTime(p.getRemainingBurstTime() - (p2.getArrivalTime() - currentTime));
                    currentTime = p2.getArrivalTime();

                    Displayer.updateTable(processes, p2.getArrivalTime());
                }
            }

            currentTime += p.getRemainingBurstTime();
            p.setStatus(Status.TERMINATED);
            p.setRemainingBurstTime(0);
            p.setTerminationTime(currentTime);
            // (process p terminates)
            Displayer.updateTable(processes, currentTime);
        }

        // finally, display the performance metrics (final results) by order of ID
        Displayer.displayPerformanceMetrics(processes);

    }
}
