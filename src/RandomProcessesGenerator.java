import java.util.ArrayList;
import java.util.Random;

public class RandomProcessesGenerator {

    private static final int MAX_BURST_DURATION = 100;
    private static final int MAX_ARRIVAL_TIME = 50;
    private static final int MAX_PRIORITY = 10;
    private static final int MAX_PROCESSES = 20;

    public static ArrayList<Process> generateProcesses() {
        Random rand = new Random();
        int numProcesses = rand.nextInt(MAX_PROCESSES) + 1;
        ArrayList<Process> processes = new ArrayList<>();

        for (int i = 0; i < numProcesses; i++) {
            int burstDuration = rand.nextInt(MAX_BURST_DURATION) + 1;
            int arrivalTime = rand.nextInt(MAX_ARRIVAL_TIME);
            int priority = rand.nextInt(MAX_PRIORITY) + 1;
            Process process = new Process(i, arrivalTime, burstDuration, priority);
            processes.add(process);
        }

        return processes;
    }
}
