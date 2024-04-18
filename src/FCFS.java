import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Thread.sleep;

// This class implements the First-Come-First-Served (FCFS) scheduling algorithm
public class FCFS {

    public void simulate(ArrayList<Process> processes) throws InterruptedException {

        // Sorting the processes based on first arrival time
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int n = processes.size();

        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            Process p = processes.get(i);
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }
            p.setStatus(Status.READY);
            // TODO display table (process just arrived)
            sleep(1000);
            p.setStatus(Status.RUNNING);

            // Waiting time of process p is the difference of when he first arrived, and now (because at the current time, process p started actually running)
            p.setWaitingTime(currentTime - p.getArrivalTime());
            // TODO display table (process started running)
            sleep(1000);
            int finishingTime = currentTime + p.getBurstTime();

            // Updating other processes (the ones who arrived while process p was running) and showing when each one arrives
            for (int j = i + 1; j < n; j++) {
                Process p2 = processes.get(j);
                if (p2.getArrivalTime() <= finishingTime) {
                    p2.setStatus(Status.READY);
                    // TODO display table (process arrives)
                    sleep(1000);
                } else {
                    break;
                }
            }

            currentTime += p.getBurstTime();
            p.setStatus(Status.TERMINATED);
            p.setTerminationTime(currentTime);
            // TODO display table (process p terminates)

            // TODO finally, display the performance metrics (final results)
        }

    }
}
