import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();

        System.out.println("Hello!");
        System.out.print("Please enter the number of processes: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("\nProcess " + (i + 1));
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();

            Process process = new Process(i + 1, arrivalTime, burstTime);
            processes.add(process);
        }

        boolean continueLoop = true;

        while (continueLoop) {
            displayTable(processes);

            System.out.println("\nWhich scheduling algorithm do you want to choose?");
            System.out.println("1. First-Come, First-Served (FCFS) (for batch systems)");
            System.out.println("2. Shortest Job First (SJF) (for batch systems)");
            System.out.println("3. Priority Scheduling (for batch systems)");
            System.out.println("4. Round Robin (RR) (for interactive systems)");
            System.out.println("5. Priority scheduling + RR (for interactive systems)");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    FCFS fcfs = new FCFS();
                    fcfs.simulate(processes);
                    break;
                case 2:
                    // Implement SJF
                    break;
                case 3:
                    // Implement Priority Scheduling
                    break;
                case 4:
                    // Implement Round Robin
                    break;
                case 5:
                    // Implement Priority Scheduling + RR
                    break;
                case 6:
                    continueLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            if (continueLoop) {
                displayPerformanceMetrics(processes);
                System.out.println("\nDo you want to choose another scheduling algorithm? (yes/no)");
                String answer = scanner.next();
                if (!answer.equalsIgnoreCase("yes")) {
                    continueLoop = false;
                }
            }
        }

        scanner.close();
    }

    private static void displayTable(ArrayList<Process> processes) {
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

    private static void displayPerformanceMetrics(ArrayList<Process> processes) {
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
