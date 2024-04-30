import javax.swing.*;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;



public class Main {

    // For printing colored text in the terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static JFileChooser fileChooser = new JFileChooser();
    public static boolean showAllSteps;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            ArrayList<Process> processes;
            try {
                processes = getProcesses(scanner);

            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Invalid choice. Please enter a number from 1 to 4." + ANSI_RESET);
                scanner.nextLine();
                continue;
            } catch (Exception e) {
                System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
                continue;
            }

            // Getting the highest priority (we need it in PriorityRoundRobin)
            int highestPriority = processes.get(0).getPriority();
            for (Process p: processes) {
                if (p.getPriority() > highestPriority) {
                    highestPriority = p.getPriority();
                }
            }

            boolean continueLoop = true;

            loopChooseAlgorithm:
            while (continueLoop) {
                // Reset the processes
                for (Process p : processes) {
                    p.reset();
                }

                Displayer.displayTable(processes, -1);

                System.out.println(ANSI_CYAN + "\nWhich scheduling algorithm do you want to choose?" + ANSI_RESET);
                System.out.println(ANSI_BLUE + "1." + ANSI_RESET + " First-Come, First-Served (FCFS) (for batch systems)");
                System.out.println(ANSI_BLUE + "2." + ANSI_RESET + " Shortest Job First (SJF) (for batch systems)");
                System.out.println(ANSI_BLUE + "3." + ANSI_RESET + " Priority Scheduling (for batch systems)");
                System.out.println(ANSI_BLUE + "4." + ANSI_RESET + " Round Robin (RR) (for interactive systems)");
                System.out.println(ANSI_BLUE + "5." + ANSI_RESET + " Priority scheduling + RR (for interactive systems)");
                System.out.println(ANSI_BLUE + "6." + ANSI_RESET + " Exit");

                int choice = 0;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println(ANSI_RED + "Invalid choice. Enter a number from 1 to 6." + ANSI_RESET);
                    scanner.nextLine();
                    continue loopChooseAlgorithm;
                }
                int quantum = 0;
                switch (choice) {
                    case 1:
                        showAllStepsPrompt();
                        FCFS fcfs = new FCFS();
                        fcfs.simulate(processes);
                        break;
                    case 2:
                        showAllStepsPrompt();
                        SJF sjf = new SJF();
                        sjf.simulate(processes);
                        break;
                    case 3:
                        showAllStepsPrompt();
                        PriorityScheduler priorityScheduler = new PriorityScheduler();
                        priorityScheduler.simulate(processes);
                        break;
                    case 4:
                        System.out.println(ANSI_CYAN + "\nEnter the quantum value: " + ANSI_RESET);
                        quantum = scanner.nextInt();  // Read the quantum from the user

                        showAllStepsPrompt();
                        RoundRobin rr = new RoundRobin(quantum);
                        rr.simulate(processes);
                        break;
                    case 5:
                        System.out.println(ANSI_CYAN + "\nEnter the quantum value: " + ANSI_RESET);
                        quantum = scanner.nextInt();

                        showAllStepsPrompt();
                        PriorityRoundRobin prr = new PriorityRoundRobin(quantum, highestPriority + 1);
                        prr.simulate(processes);
                        break;
                    case 6:
                        continueLoop = false;
                        break;
                    default:
                        System.out.println(ANSI_RED + "Invalid choice!" + ANSI_RESET);
                        continue loopChooseAlgorithm;
                }

                if (continueLoop) {
                    System.out.println(ANSI_CYAN + "\nDo you want to choose another scheduling algorithm? (yes/no)" + ANSI_RESET);
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

        System.out.println(ANSI_CYAN + "\nChoose how to input processes:" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "1." + ANSI_RESET + " Enter processes manually");
        System.out.println(ANSI_PURPLE + "2." + ANSI_RESET + " Read processes from a file");
        System.out.println(ANSI_PURPLE + "3." + ANSI_RESET + " Generate processes randomly");
        System.out.println(ANSI_PURPLE + "4." + ANSI_RESET + " Exit");

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
        System.out.println(ANSI_YELLOW + "Please select your file that contains the processes. Each line should represent a process, and each process should be comma-separated values as follows: \n" + ANSI_GREEN + "id,arrivalTime,burstTime,priority" + ANSI_RESET);
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

        System.out.println(ANSI_BLUE + "Enter processes in the format: id, arrivalTime, burstTime, priority" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "Enter 'done' to finish." + ANSI_RESET);

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
                System.out.println(ANSI_RED + "Invalid input: " + e.getMessage() + ANSI_RESET);
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Invalid input format. Please try again." + ANSI_RESET);
            }
        }

        return processes;
    }

    /**
     * Prompts the user whether he wants to be shown all steps, or get directly the final results
     */
    public static void showAllStepsPrompt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_BLUE + "Do you want to see all steps? (yes/no)" + ANSI_RESET);
        String displayChoice = scanner.nextLine().trim().toLowerCase();
        Main.showAllSteps = displayChoice.equals("yes");
    }

}
