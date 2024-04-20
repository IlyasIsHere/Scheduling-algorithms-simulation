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
            displayTable(processes);
            sleep(1000);
            p.setStatus(Status.RUNNING);
            displayTable(processes);
            sleep(1000);

            p.setWaitingTime(currentTime - p.getArrivalTime());
            int finishingTime = currentTime + p.getBurstTime();

            for (Process nextP : processes) {
                if (nextP.getArrivalTime() <= finishingTime && nextP.getStatus() == Status.NOT_ARRIVED_YET) {
                    nextP.setStatus(Status.READY);
                    displayTable(processes);
                    sleep(1000);
                }
            }

            currentTime += p.getBurstTime();
            p.setStatus(Status.TERMINATED);
            p.setTerminationTime(currentTime);
            displayTable(processes);
            sleep(1000);
        }

        displayPerformanceMetrics(processes);
    }

    private void displayTable(ArrayList<Process> processes) {
        System.out.println("\nProcess Table:");
        System.out.println("+----+--------------+------------+-----------------+");
        System.out.println("| ID | Arrival Time | Burst Time | Status          |");
        System.out.println("+----+--------------+------------+-----------------+");
        for (Process p : processes) {
            if (p.getStatus() == Status.TERMINATED) {
                System.out.printf("| %-2d | %-12d | %-10d | %-14s |\n", p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getStatus());
            } else {
                System.out.printf("| %-2d | %-12d | %-10d | %-14s |\n", p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getStatus());
            }
        }
        System.out.println("+----+--------------+------------+-----------------+");
    }

    private void displayPerformanceMetrics(ArrayList<Process> processes) {
        System.out.println("\nPerformance Metrics:");
        System.out.println("+----+--------------+------------+--------------+----------------+");
        System.out.println("| ID | Arrival Time | Burst Time | Waiting Time | Turnaround Time|");
        System.out.println("+----+--------------+------------+--------------+----------------+");
        for (Process p : processes) {
            System.out.printf("| %-2d | %-12d | %-10d | %-12d | %-14d |\n", p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getWaitingTime(), p.calcTurnaroundTime());
        }
        System.out.println("+----+--------------+------------+--------------+----------------+");
    }
}
