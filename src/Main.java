import javax.swing.JFileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();

        boolean continueFileLoop = true;
        while (continueFileLoop) {
            processes.clear();  // Clear the processes list

            System.out.println("Please select your file that contains the processes. Each line should represent a process, and each process should be comma-separated values as follows:" +
                    "{id} {arrivalTime} {burstTime} {priority}");


            try {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());

                    List<String> lines = Files.readAllLines(selectedFile.toPath());
                    for (String line : lines) {
                        String[] parts = line.split(",");
                        int id = Integer.parseInt(parts[0].trim());
                        int arrivalTime = Integer.parseInt(parts[1].trim());
                        int burstTime = Integer.parseInt(parts[2].trim());
                        int priority = Integer.parseInt(parts[3].trim());

                        Process process = new Process(id, arrivalTime, burstTime, priority);
                        processes.add(process);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
                return;
            }

            boolean continueLoop = true;

            while (continueLoop) {
                // Reset the processes
                for (Process p : processes) {
                    p.reset();
                }

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
                        FCFS fcfs = new FCFS();
                        fcfs.simulate(processes);
                        break;
                    case 2:
                        SJF sjf = new SJF();
                        sjf.simulate(processes);
                        break;
                    case 3:
                        PriorityScheduler priorityScheduler = new PriorityScheduler();
                        priorityScheduler.simulate(processes);
                        break;
                    case 4:
                        System.out.println("\nEnter the quantum value: ");
                        int quantum = scanner.nextInt();  // Read the quantum from the user

                        RoundRobin rr = new RoundRobin(quantum);
                        rr.simulate(processes);
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
                System.out.println("\nDo you want to choose another file? (yes/no)");
                String answer = scanner.next();
                if (!answer.equalsIgnoreCase("yes")) {
                    continueFileLoop = false;
                }
            }

        }
    }
}