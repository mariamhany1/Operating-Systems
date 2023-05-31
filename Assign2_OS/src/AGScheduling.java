import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Queue;

public class AGScheduling {
    int mode;
    ArrayList<process> processesList, queue;
    HashMap<Integer, ArrayList<process>> compressedProcess;
    HashMap<String, ArrayList<Integer>> quantumHistory;
    AGScheduling(ArrayList<process> processesList){
        this.processesList = processesList;
        quantumHistory = new HashMap<>();
        queue = new ArrayList<>();
        mode = 0;
        compressedProcess = new HashMap<>();
        for(int i = 0; i < processesList.size(); i++){
            if(!compressedProcess.containsKey(processesList.get(i).arrivalTime)){
                compressedProcess.put(processesList.get(i).arrivalTime, new ArrayList<>());
            }
            compressedProcess.get(processesList.get(i).arrivalTime).add(processesList.get(i));

            if(!quantumHistory.containsKey(processesList.get(i).processName)){
                quantumHistory.put(processesList.get(i).processName, new ArrayList<>());
            }
            quantumHistory.get(processesList.get(i).processName).add(processesList.get(i).quantum);
        }
    }
    void AGSchedulingProcessing(){
        int curTime = 0;
        process curProcess = null;
        int timeTaken = 0;
        int totalTime = 0;
        int priorityIndex = -1;
        int lastTime = 0;
        System.out.println("Order of Execution:");
        while (!compressedProcess.isEmpty() || !queue.isEmpty() || (curProcess != null && curProcess.burstTime > 0)){
            if(compressedProcess.containsKey(curTime)){
                for(int i = 0; i < compressedProcess.get(curTime).size(); i++){
                    queue.add(compressedProcess.get(curTime).get(i));
                }
                compressedProcess.remove(curTime);
            }
            if(curProcess == null){
                if(!queue.isEmpty()){
                    curProcess = queue.get(0);
                    queue.remove(0);
                    mode = 0;
                }
            }
            if(curProcess != null){
                if(curProcess.burstTime == 0){
                    // senario 4
                    curProcess.quantum = 0;
                    curProcess.quarterQuantum = 0;
                    quantumHistory.get(curProcess.processName).add(curProcess.quantum);
                    curProcess.completeTime = curTime;
                    curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                    curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                    System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                    System.out.println();
                    lastTime = curTime;

                    for(int i = 0; i < processesList.size();i++){
                        if(processesList.get(i).processName.equals(curProcess.processName)){
                            processesList.set(i, curProcess);
                            break;
                        }
                    }

                    if(!queue.isEmpty()){
                        curProcess = queue.get(0);
                        queue.remove(0);
                        timeTaken = 0;
                        totalTime = 0;
                        priorityIndex = -1;
                        mode = 0;
                        continue;
                    }
                }
                else if(curProcess.quantum - totalTime < 0){
                    // senario 1
                    curProcess.quantum += 2;
                    curProcess.quarterQuantum = (curProcess.quantum + 3)/4;
                    queue.add(curProcess);
                    quantumHistory.get(curProcess.processName).add(curProcess.quantum);
                    curProcess.completeTime = curTime;
                    curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                    curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                    System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                    System.out.println();
                    lastTime = curTime;
                    for(int i = 0; i < processesList.size();i++){
                        if(processesList.get(i).processName.equals(curProcess.processName)){
                            processesList.set(i, curProcess);
                            break;
                        }
                    }
                    curProcess = queue.get(0);
                    queue.remove(0);
                    timeTaken = 0;
                    totalTime = 0;
                    priorityIndex = -1;
                    mode = 0;
                    continue;
                }
                else{
                    if(curProcess.quarterQuantum == timeTaken){
                        mode++;
                        curProcess.quarterQuantum = (curProcess.quantum - totalTime + 3)/4;
                        timeTaken = 0;
                    }
                    if(mode == 0){
                        // First come first serve just continue processing
                    }
                    else if(mode == 1){
                        int tempPriority = curProcess.priority;
                        for (int i = 0; i < queue.size(); i++) {
                            if (queue.get(i).priority < tempPriority) {
                                priorityIndex = i;
                            }
                        }
                        if((timeTaken == 0 || timeTaken + 1 == curProcess.quarterQuantum) && priorityIndex != -1){
                            // senario 2
                            curProcess.quantum += (curProcess.quantum - totalTime + 1) / 2;
                            curProcess.quarterQuantum = (curProcess.quantum + 3) / 4;
                            quantumHistory.get(curProcess.processName).add(curProcess.quantum);
                            queue.add(curProcess);
                            curProcess.completeTime = curTime;
                            curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                            curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                            System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                            System.out.println();
                            lastTime = curTime;
                            for(int i = 0; i < processesList.size();i++){
                                if(processesList.get(i).processName.equals(curProcess.processName)){
                                    processesList.set(i, curProcess);
                                    break;
                                }
                            }

                            curProcess = queue.get(priorityIndex);
                            queue.remove(priorityIndex);
                            priorityIndex = -1;
                            timeTaken = 0;
                            totalTime = 0;
                            mode = 0;
                            continue;
                        }
                    }
                    else{
                        int index = -1, minBurstTime = curProcess.burstTime;
                        for (int i = 0; i < queue.size(); i++) {
                            if (queue.get(i).burstTime < minBurstTime) {
                                index = i;
                            }
                        }
                        if(index != -1){
                            // senario 3
                            curProcess.quantum += (curProcess.quantum - totalTime);
                            curProcess.quarterQuantum = (curProcess.quantum + 3)/4;
                            quantumHistory.get(curProcess.processName).add(curProcess.quantum);
                            queue.add(curProcess);
                            curProcess.completeTime = curTime;
                            curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                            curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                            System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                            System.out.println();
                            lastTime = curTime;
                            for(int i = 0; i < processesList.size();i++){
                                if(processesList.get(i).processName.equals(curProcess.processName)){
                                    processesList.set(i, curProcess);
                                    break;
                                }
                            }

                            curProcess = queue.get(index);
                            queue.remove(index);
                            timeTaken = 0;
                            totalTime = 0;
                            priorityIndex = -1;
                            mode = 0;
                            continue;
                        }
                    }
                    curProcess.burstTime--;
                    totalTime++;
                    timeTaken++;
                }
            }
            curTime++;
        }
        System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
        System.out.println();

        System.out.println("\n Answer: ");
        int sumTurnAroundTime = 0, sumWaitingTime = 0;
        for(int i = 0; i < processesList.size(); i++){
            System.out.println("Process Name: " + processesList.get(i).processName);
            System.out.println("Waiting Time: " + processesList.get(i).waitingTime);
            System.out.println("Turnaround Time: " + processesList.get(i).turnAroundTime);
            ArrayList<Integer> history = quantumHistory.get(processesList.get(i).processName);
            for(int j = 0; j < history.size(); j++){
                System.out.println("Quantum History #" + (j + 1) + " = " + history.get(j));
            }
            sumWaitingTime += processesList.get(i).waitingTime;
            sumTurnAroundTime += processesList.get(i).turnAroundTime;
            System.out.println();
        }
        double averageTurnAroundTime = (double) sumTurnAroundTime / processesList.size();
        double averageWaitingTime = (double) sumWaitingTime / processesList.size();
        System.out.println("Total Time Taken: " + (curTime));
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    }
}


//4
//4
//p1
//0
//17
//4
//7
//p2
//2
//6
//7
//9
//p3
//5
//11
//3
//4
//p4
//15
//4
//6
//6