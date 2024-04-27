import java.util.ArrayList;

abstract public class Scheduler {
    abstract public void simulate(ArrayList<Process> processes) throws InterruptedException;

    /**
     *
     * @param processes
     * @return The smallest arrival time of the processes
     */
    public int getMinArrivalTime(ArrayList<Process> processes) {
        int ans = processes.get(0).getArrivalTime();
        for (Process curr : processes) {
            if (curr.getArrivalTime() < ans) {
                ans = curr.getArrivalTime();
            }
        }
        return ans;
    }
}
