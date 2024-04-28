import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class Main {

    private static JFileChooser fileChooser = new JFileChooser();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            ArrayList<Process> processes;
            try {
                processes = getProcesses(scanner);

            } catch (InputMismatchException e) {
                System.err.println("Invalid choice. Please enter a number from 1 to 4.");
                scanner.nextLine();
                continue;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }

            boolean continueLoop = true;

            loopChooseAlgorithm:
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

                int choice = 0;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.err.println("Invalid choice. Enter a number from 1 to 6.");
                    scanner.nextLine();
                    continue loopChooseAlgorithm;
                }
                int quantum = 0;
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
                        quantum = scanner.nextInt();  // Read the quantum from the user

                        RoundRobin rr = new RoundRobin(quantum);
                        rr.simulate(processes);
                        break;
                    case 5:
                        System.out.println("\nEnter the quantum value: ");
                        quantum = scanner.nextInt();

                        System.out.println("\nEnter the number of priority levels: ");
                        int numPriorityLevels = scanner.nextInt();

                        PriorityRoundRobin prr = new PriorityRoundRobin(quantum, numPriorityLevels);
                        prr.simulate(processes);
                        break;
                    case 6:
                        continueLoop = false;
                        break;
                    default:
                        System.err.println("Invalid choice!");
                        continue loopChooseAlgorithm;
                }

                if (continueLoop) {
                    System.out.println("\nDo you want to choose another scheduling algorithm? (yes/no)");
                    String answer = scanner.next();
                    if (!answer.equalsIgnoreCase("yes")) {
                        continueLoop = false;
                    }
                }
            }
        }


        // TODO: print useful comments (process i arrives)

    }

    /**
     * This method prompts the user to choose how to input processes and returns the list of processes.
     */
    public static ArrayList<Process> getProcesses(Scanner scanner) throws Exception {

        System.out.println("\nChoose how to input processes:");
        System.out.println("1. Enter processes manually");
        System.out.println("2. Read processes from a file");
        System.out.println("3. Generate processes randomly");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                return getProcessesFromInput();
            case 2:
                return getProcessesFromFile();
            case 3:
                return RandomProcessesGenerator.generateProcesses();
            case 4:
                System.exit(0);
            default:
                throw new Exception("Invalid choice!");
        }
    }

    /**
     * This method displays a JFileChooser (Swing GUI component), so that the user uploads a file that contains the processes in a comma-separated format.
     * <p>
     * Each line should be of this form: process_id,arrivalTime,burstTime,priority
     * <p>
     * If the user doesn't want to work with priorities, he may simply put 0 or any random positive number in that field.
     *
     * @return An ArrayList of processes that were read from the file
     */
    public static ArrayList<Process> getProcessesFromFile() throws Exception {
        System.out.println("Please select your file that contains the processes. Each line should represent a process, and each process should be comma-separated values as follows: \nid,arrivalTime,burstTime,priority");
        ArrayList<Process> processes = new ArrayList<>();

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

        if (processes.isEmpty()) {
            throw new Exception("The file you provided is empty or you have provided no file at all!");
        }

        return processes;
    }

    /**
     * Gets processes from user input in the terminal.
     *
     * @return An ArrayList of processes entered by the user.
     */
    public static ArrayList<Process> getProcessesFromInput() {
        ArrayList<Process> processes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter processes in the format: id, arrivalTime, burstTime, priority");
        System.out.println("Enter 'done' to finish.");

        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
            String[] parts = input.split(",");
            try {
                int id = Integer.parseInt(parts[0].trim());
                int arrivalTime = Integer.parseInt(parts[1].trim());
                int burstTime = Integer.parseInt(parts[2].trim());
                int priority = Integer.parseInt(parts[3].trim());
                processes.add(new Process(id, arrivalTime, burstTime, priority));
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("Invalid input format. Please try again.");
            }
        }

        return processes;
    }

}
