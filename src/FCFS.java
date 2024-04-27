import java.util.ArrayList;
import java.util.Comparator;
import static java.lang.Thread.sleep;

// This class implements the First-Come-First-Served (FCFS) scheduling algorithm
public class FCFS {

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
        for (int i = 0; i < n; i++) {
            Process p = processes.get(i);
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
                p.setStatus(Status.READY);
                // (process just arrived)
                Displayer.displayTable(processes, currentTime);
            }

            p.setStatus(Status.RUNNING);

            // Waiting time of process p is the difference of when it first arrived, and now (because at the current time, process p started actually running)
            p.setWaitingTime(currentTime - p.getArrivalTime());

            // (process started running)
            Displayer.displayTable(processes, currentTime);
            int finishingTime = currentTime + p.getBurstTime();

            // Updating other processes (the ones who arrived while process p was running) and showing when each one arrives
            for (int j = i + 1; j < n; j++) {
                Process p2 = processes.get(j);
                if (p2.getArrivalTime() <= finishingTime && p2.getStatus() == Status.NOT_ARRIVED_YET) {
                    p2.setStatus(Status.READY);
                    // (process arrives)

                    Displayer.displayTable(processes, p2.getArrivalTime());
                }
            }

            currentTime += p.getBurstTime();
            p.setStatus(Status.TERMINATED);
            p.setTerminationTime(currentTime);
            // (process p terminates)
            Displayer.displayTable(processes, currentTime);
        }

        // finally, display the performance metrics (final results) by order of ID
        Displayer.displayPerformanceMetrics(processes);

    }
}
