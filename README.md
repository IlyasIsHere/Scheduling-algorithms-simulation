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

### About what we did in each algorithm
  ## First Come First Serve (FCFS)
**File:** `FCFS.java`

**Description:**  
FCFS is the simplest type of CPU scheduling algorithm that operates on a first-come, first-served basis. The algorithm queues processes in the order they arrive in the ready queue and runs them until completion without preemption.

**Implementation Details:**
- **Process Queueing:** Processes are added to a queue as they arrive.
- **Execution:** The scheduler picks the first process from the queue and executes it. The process runs until it finishes, and then the scheduler selects the next process.
- **No Preemption:** Once a process starts running, it continues until it finishes.

**Step-by-Step Code Explanation:**
1. Initialize a queue to hold the processes.
2. Sort the processes by their arrival time to ensure they are handled in the correct order.
3. Continuously fetch and execute the first process in the queue until all processes are completed.

## Shortest Job First (SJF)
**File:** `SJF.java`

**Description:**  
SJF can be either preemptive or non-preemptive. It prioritizes processes with the shortest execution time first, which helps to minimize the average waiting time for all processes.

**Implementation Details:**
- **Process Selection:** Selects the process with the shortest burst time from the ready queue.
- **Preemptive/Non-Preemptive:** In preemptive mode, the current running process can be interrupted if a new process with a shorter burst time arrives.

**Step-by-Step Code Explanation:**
1. Monitor arriving processes and maintain a priority queue based on burst time.
2. For non-preemptive, execute the shortest job completely before moving on to the next.
3. For preemptive, compare the burst time of the currently running process with new arrivals and preempt if necessary.

## Round Robin (RR)
**File:** `RoundRobin.java`

**Description:**  
Round Robin is a preemptive scheduling algorithm that assigns a fixed time slice (quantum) to each process in a cyclic order. It is designed to provide fairness among processes.

**Implementation Details:**
- **Time Quantum:** Each process gets an equal time slice, after which it is placed back in the queue if it hasn’t finished execution.
- **Cyclic Ordering:** Processes are executed in a cyclic manner, ensuring all processes receive CPU time in rounds.

**Step-by-Step Code Explanation:**
1. Use a queue to manage the processes.
2. Execute each process for a time equal to the quantum.
3. If the process doesn’t finish, requeue it at the end.
4. Continue this cycle until all processes are finished.

## Priority Scheduling
**File:** `PriorityScheduler.java`

**Description:**  
Processes are scheduled according to their priority. Higher priority processes are executed first. If priorities are equal, FCFS principle is applied.

**Implementation Details:**
- **Priority Levels:** Each process is assigned a priority level.
- **Process Selection:** The scheduler picks the process with the highest priority from the queue.

**Step-by-Step Code Explanation:**
1. Maintain a priority queue where processes are automatically sorted by priority.
2. Execute the highest priority process available in the queue.
3. If two processes have the same priority, apply FCFS to decide which runs first.

## Combining Algorithms: Priority Round Robin
**File:** `PriorityRoundRobin.java`

**Description:**  
This hybrid approach combines priority scheduling with round robin. Processes are first grouped based on priority, and then each group is scheduled using the round robin method.

**Implementation Details:**
- **Grouping by Priority:** Processes are divided into groups based on their priority.
- **Round Robin Execution:** Within each group, processes are scheduled in a round-robin fashion using a fixed quantum.

**Step-by-Step Code Explanation:**
1. Categorize processes into priority levels.
2. Apply round robin scheduling within each priority level, ensuring that processes in higher priority levels are selected before those in lower levels.

  
## Usage Example
Run the application and follow the prompts to select an algorithm and provide process details. The simulator will execute and provide a detailed display of the scheduling order and metrics.

## Compilation and Execution Instructions
To compile and run the simulation:
1. Ensure you have Java installed on your system.
2. Navigate to the directory containing the source files.
3. Compile the Java files using the following command.
4. Run the simulation by executing the `Main` class.
5. Follow the on-screen options to choose different scheduling algorithms or test scenarios. If you want to test the algorithms with specific process sets, you can use the provided `file.txt` examples or create new ones and upload them to test their behavior.

