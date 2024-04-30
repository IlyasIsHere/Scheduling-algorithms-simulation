import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Thread.sleep;

public class Displayer {

    static void updateTable(ArrayList<Process> processes, int currentTime) throws InterruptedException {

        // Printing ANSI escape characters to move back to the start of the table, in order to overwrite the values outputted, and make the table dynamic.
        // It only works on ANSI terminals.
        if (Main.showAllSteps) {
            String goback = "\033[F".repeat(processes.size() + 7);
            System.out.print(goback);
        }

        displayTable(processes, currentTime);
    }

    static void displayTable(ArrayList<Process> processes, int currentTime) throws InterruptedException {

        if (Main.showAllSteps || currentTime == -1) {
            System.out.println("\nProcess Table:");
            System.out.println("Current Time: " + (currentTime >= 0 ? currentTime : "Not yet started"));
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
        System.out.println(Main.ANSI_CYAN + "\nPerformance Metrics:" + Main.ANSI_RESET);
        System.out.println("+----+--------------+------------+--------------+-----------------+");
        System.out.println(Main.ANSI_YELLOW + "| %s | Arrival Time | Burst Time | Waiting Time | Turnaround Time |" + Main.ANSI_RESET);
        System.out.println("+----+--------------+------------+--------------+-----------------+");
        for (Process p : processes) {
            System.out.printf("| %-2d | %-12d | %-10d | %-12d | %-15d |\n", p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getWaitingTime(), p.calcTurnaroundTime());
        }
        System.out.println("+----+--------------+------------+--------------+-----------------+");

        // Calculate and display the mean turnaround time, and mean waiting time
        double meanTurnaroundTime = calculateMeanTurnaroundTime(processes);
        double meanWaitingTime = calculateMeanWaitingTime(processes);
        System.out.println(Main.ANSI_YELLOW + "\nAverage Turnaround Time: " + meanTurnaroundTime + Main.ANSI_RESET);
        System.out.println(Main.ANSI_YELLOW + "Average Waiting Time: " + meanWaitingTime + Main.ANSI_RESET);
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

    /**
     * Calculates the CPU usage during the execution of the processes.
     * @param processes The ArrayList of processes, with their startingTime and terminationTime set.
     * @return CPU usage
     */
//    public static double calculateCpuUsage(ArrayList<Process> processes) {
//
//    }
}


