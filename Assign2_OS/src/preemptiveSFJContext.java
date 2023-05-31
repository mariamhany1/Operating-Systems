import java.util.*;

public class preemptiveSFJContext {
    public ArrayList<process> processesList, queue;
    ArrayList<Integer> arrivalTime;
    HashMap<Integer, ArrayList<process>> compressedProcess;

    preemptiveSFJContext(ArrayList<process> processesList) {
        this.processesList = processesList;
        arrivalTime = new ArrayList<>();
        queue = new ArrayList<>();
        compressedProcess = new HashMap<>();
        for(int i = 0; i < processesList.size(); i++){
            if(!compressedProcess.containsKey(processesList.get(i).arrivalTime)){
                compressedProcess.put(processesList.get(i).arrivalTime, new ArrayList<>());
            }
            compressedProcess.get(processesList.get(i).arrivalTime).add(processesList.get(i));
        }
    }

    public void preemptiveSFJ(int context) {
        int curTime = 0, contextSwitching = 0, lastTime = 0;
        process curProcess = null;
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
                }
            }
            else{
                int index = -1, execution = curProcess.burstTime, arrival = curProcess.arrivalTime;
                for(int i = 0; i < queue.size(); i++){
                    if(queue.get(i).burstTime < execution){
                        execution = queue.get(i).burstTime;
                        arrival = queue.get(i).arrivalTime;
                        index = i;
                    }
                    else if(queue.get(i).burstTime == execution && execution != curProcess.burstTime
                        && queue.get(i).arrivalTime < arrival){
                        arrival = queue.get(i).arrivalTime;
                        index = i;
                    }
                }
                if(index != -1){
                    curProcess.completeTime = curTime;
                    curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                    curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                    if(curProcess.burstTime > 0){
                        queue.add(curProcess);
                    }
                    System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                    System.out.println("Context Switch from " + curTime + " to " + (curTime + context));
                    System.out.println();
                    contextSwitching += context;
                    lastTime = curTime;
                    curProcess = queue.get(index);
                    queue.remove(index);
                    for (int i = curTime; i <= curTime + context; i++){
                        if(compressedProcess.containsKey(i)){
                            for(int j = 0; j < compressedProcess.get(i).size(); j++){
                                queue.add(compressedProcess.get(i).get(j));
                            }
                            compressedProcess.remove(i);
                        }
                    }
                    curTime += context;
                    continue;
                }
            }
            if(curProcess != null){
                if(curProcess.burstTime == 0){
                    curProcess.completeTime = curTime;
                    curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                    curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                    for(int i = 0; i < processesList.size();i++){
                        if(processesList.get(i).processName.equals(curProcess.processName)){
                            processesList.set(i, curProcess);
                            break;
                        }
                    }
                    System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                    System.out.println("Context Switch from " + curTime + " to " + (curTime + context));
                    System.out.println();
                    contextSwitching += context;
                    for (int i = curTime; i <= curTime + context; i++){
                        if(compressedProcess.containsKey(i)){
                            for(int j = 0; j < compressedProcess.get(i).size(); j++){
                                queue.add(compressedProcess.get(i).get(j));
                            }
                            compressedProcess.remove(i);
                        }
                    }
                    curTime += context;
                    lastTime = curTime;
                    curProcess = null;
                    if(!queue.isEmpty()) {
                        int index = -1, execution = queue.get(0).burstTime, arrival = queue.get(0).arrivalTime;
                        for (int i = 1; i < queue.size(); i++) {
                            if(queue.get(i).burstTime < execution){
                                execution = queue.get(i).burstTime;
                                arrival = queue.get(i).arrivalTime;
                                index = i;
                            }
                            else if(queue.get(i).burstTime == execution && queue.get(i).arrivalTime < arrival){
                                arrival = queue.get(i).arrivalTime;
                                index = i;
                            }
                        }
                        if(index != -1){
                            curProcess = queue.get(index);
                            queue.remove(index);
                        }
                        else{
                            curProcess = queue.get(0);
                            queue.remove(0);
                        }
                    }
                    continue;
                }
                else{
                    curProcess.burstTime--;
                }
            }
            curTime++;
            if(queue.isEmpty() && compressedProcess.isEmpty() && (curProcess != null&&curProcess.burstTime == 0)){
                curProcess.completeTime = curTime;
                curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                for(int i = 0; i < processesList.size();i++){
                    if(processesList.get(i).processName.equals(curProcess.processName)){
                        processesList.set(i, curProcess);
                        break;
                    }
                }
                System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                System.out.println("Context Switch from " + curTime + " to " + (curTime + context));
                System.out.println();
                contextSwitching += context;
                break;
            }
        }
        System.out.println("\n Answer: ");
        int sumTurnAroundTime = 0, sumWaitingTime = 0;
        for(int i = 0; i < processesList.size(); i++){
            System.out.println("Process Name: " + processesList.get(i).processName);
            System.out.println("Waiting Time: " + processesList.get(i).waitingTime);
            System.out.println("Turnaround Time: " + processesList.get(i).turnAroundTime);
            sumWaitingTime += processesList.get(i).waitingTime;
            sumTurnAroundTime += processesList.get(i).turnAroundTime;
            System.out.println();
        }
        double averageTurnAroundTime = (double) sumTurnAroundTime / processesList.size();
        double averageWaitingTime = (double) sumWaitingTime / processesList.size();
        System.out.println("Total Time Taken: " + (curTime + contextSwitching));
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    }
}


//Test Case
//1
//5
//0
//p1
//2
//6
//1
//p2
//5
//2
//1
//p3
//1
//8
//1
//p4
//0
//3
//1
//p5
//4
//4
//1

