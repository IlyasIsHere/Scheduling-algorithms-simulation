import java.util.ArrayList;

public class Displayer {

    static void displayTable(ArrayList<Process> processes, int currentTime) {
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
    }

    static void displayPerformanceMetrics(ArrayList<Process> processes) {
        System.out.println("\nPerformance Metrics:");
        System.out.println("+----+--------------+------------+--------------+----------------+");
        System.out.println("| ID | Arrival Time | Burst Time | Waiting Time | Turnaround Time|");
        System.out.println("+----+--------------+------------+--------------+----------------+");
        for (Process p : processes) {
            System.out.printf("| %-2d | %-12d | %-10d | %-12d | %-14d |\n", p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getWaitingTime(), p.calcTurnaroundTime());
        }
        System.out.println("+----+--------------+------------+--------------+----------------+");

        // TODO: averages...
    }
}
