import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> originalProcesses = new ArrayList<>();

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
            originalProcesses.add(process);
        }

        boolean continueLoop = true;

        while (continueLoop) {
            // Create a copy of the original processes ArrayList
            ArrayList<Process> processes = new ArrayList<>(originalProcesses);

            Displayer.displayTable(processes, -1);

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
                    // Reset the statuses
                    for (Process p : processes) {
                        p.setStatus(Status.NOT_ARRIVED_YET);
                    }
                    FCFS fcfs = new FCFS();
                    fcfs.simulate(processes);
                    break;
                case 2:
                    // Reset the statuses
                    /*for (Process p : processes) {
                        p.setStatus(Status.NOT_ARRIVED_YET);
                    }
                    SJF sjf = new SJF();
                    sjf.simulate(processes);
                    break;*/
                    SJF sjf = new SJF();
                    sjf.simulate(new ArrayList<>(processes));
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
                System.out.println("\nDo you want to choose another scheduling algorithm? (yes/no)");
                String answer = scanner.next();
                if (!answer.equalsIgnoreCase("yes")) {
                    continueLoop = false;
                }
            }
        }

        scanner.close();
    }
}

