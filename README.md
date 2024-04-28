# Scheduling Algorithms Simulation

## Description
This project simulates various process scheduling algorithms to study their behaviors and performance characteristics. The program includes a variety of schedulers, each implemented in a separate Java class, along with utility classes for process generation and display.

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

## Compilation and Execution Instructions
To compile and run the simulation:
1. Ensure you have Java installed on your system.
2. Navigate to the directory containing the source files.
3. Compile the Java files using the following command.
4. Run the simulation by executing the `Main` class.
5. Follow the on-screen options to choose different scheduling algorithms or test scenarios. If you want to test the algorithms with specific process sets, you can use the provided `file.txt` examples or create new ones and upload them to test their behavior.

