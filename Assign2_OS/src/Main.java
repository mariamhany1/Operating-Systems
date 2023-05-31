import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("1- SJF");
        System.out.println("2- Round Robin");
        System.out.println("3- Priority Scheduling");
        System.out.println("4- AG Scheduling");
        int type = input.nextInt();
        System.out.print("Enter the number of processes : ");
        int processesNumber;
        processesNumber = input.nextInt();
        System.out.println("Enter the data for each process");
        ArrayList<process> processesList = new ArrayList<>();
        if(type == 1){
            System.out.print("Enter the context switch time : ");
            int context = input.nextInt();
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime, priority;
                System.out.print("Enter the name : ");
                processName = input.next();
                System.out.print("Enter the arrival time : ");
                arrivalTime = input.nextInt();
                System.out.print("Enter the burst time : ");
                burstTime = input.nextInt();
                System.out.print("Enter the priority : ");
                priority = input.nextInt();
                process process = new process(processName, arrivalTime, burstTime, priority);
                processesList.add(process);

            }
            System.out.println("\n");
            preemptiveSFJContext preemptiveSFJContext = new preemptiveSFJContext(processesList);
            preemptiveSFJContext.preemptiveSFJ(context);
        }
        else if(type == 2){

            System.out.println("Enter Quantum time:");
            int quantum = input.nextInt();
            System.out.println("Enter context switching time :");
            int context = input.nextInt();

            ////////////
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime, priority;
                System.out.print("Enter the name : ");
                processName = input.next();
                System.out.print("Enter the arrival time : ");
                arrivalTime = input.nextInt();
                System.out.print("Enter the burst time : ");
                burstTime = input.nextInt();
                System.out.print("Enter the priority : ");
                priority = input.nextInt();
                process process = new process(processName, arrivalTime, burstTime, priority);
                processesList.add(process);

            }
            RoundRobin roundRobin = new RoundRobin(processesList);
            roundRobin.roundRobin(quantum, context);

        }
        else if (type == 3){
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime, priority;
                System.out.print("Enter the name : ");
                processName = input.next();
                System.out.print("Enter the arrival time : ");
                arrivalTime = input.nextInt();
                System.out.print("Enter the burst time : ");
                burstTime = input.nextInt();
                System.out.print("Enter the priority time : ");
                priority = input.nextInt();
                process process = new process(processName, arrivalTime, burstTime, priority);
                processesList.add(process);
            }
            priorityScheduling priorityScheduling = new priorityScheduling(processesList);
            priorityScheduling.priorityScheduling();
        }
        else{
            for (int i = 0; i < processesNumber; i++) {
                String processName;
                int burstTime, arrivalTime, priority, quantum;
                System.out.print("Enter the name : ");
                processName = input.next();
                System.out.print("Enter the arrival time : ");
                arrivalTime = input.nextInt();
                System.out.print("Enter the burst time : ");
                burstTime = input.nextInt();
                System.out.print("Enter the priority time : ");
                priority = input.nextInt();
                System.out.print("Enter the Quantum time : ");
                quantum = input.nextInt();
                process process = new process(processName, arrivalTime, burstTime, priority, quantum);
                processesList.add(process);
            }
            AGScheduling agScheduling = new AGScheduling(processesList);
            agScheduling.AGSchedulingProcessing();
        }
    }
}

