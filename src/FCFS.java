import java.util.ArrayList;
import java.util.Comparator;

// This class implements the First-Come-First-Served (FCFS) scheduling algorithm
public class FCFS {
    public void simulate(ArrayList<Process> processes) {
        processes.sort(new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        for (Process p: processes) {

        }
    }
}
