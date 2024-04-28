import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Thread.sleep;

public class Displayer {

    static void updateTable(ArrayList<Process> processes, int currentTime) throws InterruptedException {
        String goback = "\033[F".repeat(processes.size() + 7);
        System.out.print(goback);

        displayTable(processes, currentTime);
    }

    static void displayTable(ArrayList<Process> processes, int currentTime) throws InterruptedException {
        System.out.println("\nProcess Table:");
        if (currentTime >= 0) {
            System.out.println("Current Time: " + currentTime);
        } else {
            System.out.println("Current Time: Not yet started");
        }
        System.out.println("+----+----------+--------------+------------+--------------+---------------------+");
        System.out.println("| ID | Priority | Arrival Time | Burst Time | Rem. Burst   |     Status          |");
        System.out.println("+----+----------+--------------+------------+--------------+---------------------+");

        for (Process p : processes) {
            System.out.printf("| %-2d | %-8d | %-12d | %-10d | %-12d | %-19s |\n",
                    p.getId(), p.getPriority(), p.getArrivalTime(), p.getBurstTime(), p.getRemainingBurstTime(), p.getStatus());
        }

        System.out.println("+----+----------+--------------+------------+--------------+---------------------+");
        sleep(1000);
    }

    static void displayTable(ArrayList<Process> remaining, ArrayList<Process> terminated, int currentTime) throws InterruptedException {
        ArrayList<Process> processes = new ArrayList<>();
        processes.addAll(remaining);
        processes.addAll(terminated);

        // Sorting by arrival time (for displaying purposes)
        processes.sort(Comparator.comparingInt(Process::getId));
        updateTable(processes, currentTime);
    }

    static void displayPerformanceMetrics(ArrayList<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getId));
        System.out.println("\nPerformance Metrics:");
        System.out.println("+----+--------------+------------+--------------+-----------------+");
        System.out.println("| ID | Arrival Time | Burst Time | Waiting Time | Turnaround Time |");
        System.out.println("+----+--------------+------------+--------------+-----------------+");
        for (Process p : processes) {
            System.out.printf("| %-2d | %-12d | %-10d | %-12d | %-15d |\n", p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getWaitingTime(), p.calcTurnaroundTime());
        }
        System.out.println("+----+--------------+------------+--------------+-----------------+");

        // Calculate and display the mean turnaround time, and mean waiting time
        double meanTurnaroundTime = calculateMeanTurnaroundTime(processes);
        double meanWaitingTime = calculateMeanWaitingTime(processes);
        System.out.println("\nAverage Turnaround Time: " + meanTurnaroundTime);
        System.out.println("Average Waiting Time: " + meanWaitingTime);
    }

    public static double calculateMeanTurnaroundTime(ArrayList<Process> processes) {
        int totalTurnaroundTime = 0;
        for (Process p : processes) {
            totalTurnaroundTime += p.calcTurnaroundTime();
        }
        return (double) totalTurnaroundTime / processes.size();
    }

    public static double calculateMeanWaitingTime(ArrayList<Process> processes) {
        int totalWaitingTime = 0;
        for (Process p : processes) {
            totalWaitingTime += p.getWaitingTime();
        }
        return (double) totalWaitingTime / processes.size();
    }
}


