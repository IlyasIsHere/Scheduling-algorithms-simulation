///*import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Scanner;
//import static java.lang.Thread.sleep;
//
//public class SJF {
//
//    public void simulate(ArrayList<Process> processes) throws InterruptedException {
//
//        // Sorting the processes based on burst time
//        processes.sort(Comparator.comparingInt(Process::getBurstTime));
//
//        int n = processes.size();
//
//        int currentTime = 0;
//        for (int i = 0; i < n; i++) {
//            Process p = processes.get(i);
//            if (currentTime < p.getArrivalTime()) {
//                currentTime = p.getArrivalTime();
//                p.setStatus(Status.READY);
//                // (process just arrived)
//                Displayer.displayTable(processes, currentTime);
//                sleep(1000);
//            }
//
//            p.setStatus(Status.RUNNING);
//
//            // Waiting time of process p is the difference of when it first arrived, and now (because at the current time, process p started actually running)
//            p.setWaitingTime(currentTime - p.getArrivalTime());
//
//            // (process started running)
//            Displayer.displayTable(processes, currentTime);
//            sleep(1000);
//            int finishingTime = currentTime + p.getBurstTime();
//
//            // Updating other processes (the ones who arrived while process p was running) and showing when each one arrives
//            for (int j = i + 1; j < n; j++) {
//                Process p2 = processes.get(j);
//                if (p2.getArrivalTime() <= finishingTime && p2.getStatus() == Status.NOT_ARRIVED_YET) {
//                    p2.setStatus(Status.READY);
//                    // (process arrives)
//
//                    Displayer.displayTable(processes, p2.getArrivalTime());
//                    sleep(1000);
//                }
//            }
//
//            currentTime += p.getBurstTime();
//            p.setStatus(Status.TERMINATED);
//            p.setTerminationTime(currentTime);
//            // (process p terminates)
//            Displayer.displayTable(processes, currentTime);
//        }
//
//        // finally, display the performance metrics (final results) by order of ID
//        processes.sort(Comparator.comparingInt(Process::getId));
//        Displayer.displayPerformanceMetrics(processes);
//
//    }
//}*/
//
//import java.util.ArrayList;
//import java.util.Comparator;
//
//public class SJF {
//
//    public void simulate(ArrayList<Process> processes) {
//
//        // Sorting the processes based on arrival time and burst time
//        processes.sort(new SJFComparator());
//
//        int currentTime = 0;
//        ArrayList<Process> readyQueue = new ArrayList<>();
//        ArrayList<Process> completedProcesses = new ArrayList<>();
//
//        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
//
//            // Add newly arrived processes to the ready queue
//            while (!processes.isEmpty() && processes.get(0).getArrivalTime() <= currentTime) {
//                readyQueue.add(processes.remove(0));
//            }
//
//            if (!readyQueue.isEmpty()) {
//                Process shortestJob = readyQueue.get(0);
//
//                // Find the shortest job in the ready queue
//                for (Process p : readyQueue) {
//                    if (p.getBurstTime() < shortestJob.getBurstTime()) {
//                        shortestJob = p;
//                    }
//                }
//
//                readyQueue.remove(shortestJob);
//                shortestJob.setStatus(Status.RUNNING);
//                shortestJob.setWaitingTime(currentTime - shortestJob.getArrivalTime());
//
//                // Display the current state
//                shortestJob.setTerminationTime(currentTime);
//                completedProcesses.add(shortestJob);
//                Displayer.displayTable(completedProcesses, currentTime);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // Update the completion time and status of the shortest job
//                currentTime += shortestJob.getBurstTime();
//                shortestJob.setStatus(Status.TERMINATED);
//            } else {
//                currentTime++;
//            }
//        }
//
//        // Display the performance metrics
//        Displayer.displayPerformanceMetrics(completedProcesses);
//    }
//
//    public static class SJFComparator implements Comparator<Process> {
//
//        @Override
//        public int compare(Process o1, Process o2) {
//            if (o1.getArrivalTime() != o2.getArrivalTime()) {
//                return o1.getArrivalTime() - o2.getArrivalTime();
//            }
//            return o1.getBurstTime() - o2.getBurstTime();
//        }
//    }
//}


import java.util.ArrayList;
import java.util.Comparator;
import static java.lang.Thread.sleep;

public class SJF {

    public void simulate(ArrayList<Process> processes) throws InterruptedException {

        // Sorting the processes based on burst time (shortest job first)
        processes.sort(Comparator.comparingInt(Process::getBurstTime));

        int n = processes.size();

        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            Process p = processes.get(i);
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
                p.setStatus(Status.READY);
                // (process just arrived)
                Displayer.displayTable(processes, currentTime);
                sleep(1000);
            }

            p.setStatus(Status.RUNNING);

            // Waiting time of process p is the difference of when it first arrived, and now (because at the current time, process p started actually running)
            p.setWaitingTime(currentTime - p.getArrivalTime());

            // (process started running)
            Displayer.displayTable(processes, currentTime);
            sleep(1000);
            int finishingTime = currentTime + p.getBurstTime();

            // Updating other processes (the ones who arrived while process p was running) and showing when each one arrives
            for (int j = i + 1; j < n; j++) {
                Process p2 = processes.get(j);
                if (p2.getArrivalTime() <= finishingTime && p2.getStatus() == Status.NOT_ARRIVED_YET) {
                    p2.setStatus(Status.READY);
                    // (process arrives)

                    Displayer.displayTable(processes, p2.getArrivalTime());
                    sleep(1000);
                }
            }

            currentTime += p.getBurstTime();
            p.setStatus(Status.TERMINATED);
            p.setTerminationTime(currentTime);
            // (process p terminates)
            Displayer.displayTable(processes, currentTime);
        }

        // finally, display the performance metrics (final results) by order of ID
        processes.sort(Comparator.comparingInt(Process::getId));
        Displayer.displayPerformanceMetrics(processes);

    }
}