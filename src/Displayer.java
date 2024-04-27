import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Thread.sleep;

public class Displayer {

    static void displayTable(ArrayList<Process> processes, int currentTime) throws InterruptedException {
        System.out.println("\nProcess Table:");
        if (currentTime >= 0) {
            System.out.println("Current Time: " + currentTime);
        }
        System.out.println("+----+--------------+------------+-----------------+");
        System.out.println("| ID | Arrival Time | Burst Time | Status          |");
        System.out.println("+----+--------------+------------+-----------------+");
        for (Process p : processes) {
            System.out.printf("| %-2d | %-12d | %-10d | %-14s |\n", p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getStatus());
        }
        System.out.println("+----+--------------+------------+-----------------+");
        sleep(1000);

        // TODO: remaining burst time
    }

    static void displayTable(ArrayList<Process> remaining, ArrayList<Process> terminated, int currentTime) throws InterruptedException {
        ArrayList<Process> processes = new ArrayList<>();
        processes.addAll(remaining);
        processes.addAll(terminated);

        // Sorting by arrival time (for displaying purposes)
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        displayTable(processes, currentTime);
    }

    static void displayPerformanceMetrics(ArrayList<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getId));
        System.out.println("\nPerformance Metrics:");
        System.out.println("+----+--------------+------------+--------------+----------------+");
        System.out.println("| ID | Arrival Time | Burst Time | Waiting Time | Turnaround Time|");
        System.out.println("+----+--------------+------------+--------------+----------------+");
        for (Process p : processes) {
            System.out.printf("| %-2d | %-12d | %-10d | %-12d | %-14d |\n", p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getWaitingTime(), p.calcTurnaroundTime());
        }
        System.out.println("+----+--------------+------------+--------------+----------------+");

        // Calculate and display the mean turnaround time
        double meanTurnaroundTime = calculateMeanTurnaroundTime(processes);
        System.out.println("\nMean Turnaround Time: " + meanTurnaroundTime);
    }

    public static double calculateMeanTurnaroundTime(ArrayList<Process> processes) {
        int totalTurnaroundTime = 0;
        for (Process p : processes) {
            totalTurnaroundTime += p.calcTurnaroundTime();
        }
        return (double) totalTurnaroundTime / processes.size();
    }
}


