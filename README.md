# CPU Scheduler Simulator

## Description
The CPU Scheduler Simulator is designed to simulate different CPU scheduling algorithms, providing insights into how various algorithms prioritize and manage processes. This tool is valuable for students and educators in computer science, particularly in operating systems courses.

## Components
- **Main.java**: Entry point that orchestrates the simulation, utilizing various schedulers.
- **Process.java**: Represents a process with attributes necessary for scheduling.
- **Scheduler.java**: Abstract class defining essential scheduler operations.
- **Displayer.java**: Handles the output of scheduling results.
- **RandomProcessesGenerator.java**: Generates a set of random processes to be used in simulations.
- **FCFS.java**: Implements the First-Come, First-Served scheduling algorithm.
- **SJF.java**: Implements the Shortest Job First scheduling algorithm.
- **RoundRobin.java**: Implements the Round Robin scheduling algorithm.
- **PriorityScheduler.java**: Schedules processes based on a priority system.
- **PriorityRoundRobin.java**: Combines the Priority and Round Robin scheduling methods for a hybrid approach.

## Algorithm Descriptions
- **First Come First Serve (FCFS)**: This algorithm schedules processes in the order they arrive in the ready queue without preemption. It is simple and fair, but can lead to long waiting times, particularly for processes arriving just behind a process with a long burst time.
- **Shortest Job First (SJF)**: This preemptive or non-preemptive approach schedules processes with the shortest expected processing time first, minimizing the average time a process spends waiting in the ready queue.
- **Round Robin (RR)**: This algorithm assigns a fixed time unit per process and cycles through them. If a process's burst time exceeds the fixed time, it is placed back in the queue to wait for the next turn. This ensures responsiveness and reduces the penalty for longer processes.
- **Priority Scheduling:** Processes are assigned priorities. A process with higher priority is processed first. If two processes have the same priority, FCFS rules are applied. Priority scheduling can be preemptive or non-preemptive.
  
## Usage Example
Run the application and follow the prompts to select an algorithm and provide process details. The simulator will execute and provide a detailed display of the scheduling order and metrics.

## Compilation and Execution Instructions
To compile and run the simulation:
1. Ensure you have Java installed on your system.
2. Navigate to the directory containing the source files.
3. Compile the Java files using the following command.
4. Run the simulation by executing the `Main` class.
5. Follow the on-screen options to choose different scheduling algorithms or test scenarios. If you want to test the algorithms with specific process sets, you can use the provided `file.txt` examples or create new ones and upload them to test their behavior.

