import java.util.*;

public class RoundRobin {

    ArrayList<process> processesList, queue;
    HashMap<Integer, ArrayList<process>> compressedProcess;


    RoundRobin(ArrayList<process> processesList) {
        this.processesList = processesList;
        queue = new ArrayList<>();
        compressedProcess = new HashMap<>();
        for(int i = 0; i < processesList.size(); i++){
            if(!compressedProcess.containsKey(processesList.get(i).arrivalTime)){
                compressedProcess.put(processesList.get(i).arrivalTime, new ArrayList<>());
            }
            compressedProcess.get(processesList.get(i).arrivalTime).add(processesList.get(i));
        }
    }


    public void roundRobin(int quantum, int context)
    {
        int curTime = 0;
        int lastTime = 0;
        int timeTaken = 0;
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
            if(curProcess != null) {
                if (curProcess.burstTime == 0) {
                    System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                    System.out.println("Context Switch from " + curTime + " to " + (curTime + context));
                    curProcess.completeTime = curTime + context;
                    curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                    curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                    System.out.println();
                    timeTaken = 0;
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
                    if (!queue.isEmpty()) {
                        curProcess = queue.get(0);
                        queue.remove(0);
                    } else {
                        curProcess = null;
                    }
                    continue;
                } else if (timeTaken == quantum) {
                    System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                    System.out.println("Context Switch from " + curTime + " to " + (curTime + context));
                    curProcess.completeTime = curTime + context;
                    curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                    curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                    System.out.println();
                    for (int i = curTime; i <= curTime + context; i++){
                        if(compressedProcess.containsKey(i)){
                            for(int j = 0; j < compressedProcess.get(i).size(); j++){
                                queue.add(compressedProcess.get(i).get(j));
                            }
                            compressedProcess.remove(i);
                        }
                    }
                    curTime += context;
                    queue.add(curProcess);
                    curProcess = queue.get(0);
                    queue.remove(0);
                    timeTaken = 0;
                    lastTime = curTime;
                    continue;
                } else {
                    curProcess.burstTime--;
                }
                timeTaken++;
            }
            curTime++;
            if(queue.isEmpty() && compressedProcess.isEmpty() && (curProcess != null&&curProcess.burstTime == 0)){
                System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                System.out.println("Context Switch from " + curTime + " to " + (curTime + context));
                curTime += context;
                curProcess.completeTime = curTime;
                curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                for(int i = 0; i < processesList.size();i++){
                    if(processesList.get(i).processName.equals(curProcess.processName)){
                        processesList.set(i, curProcess);
                        break;
                    }
                }
                System.out.println();
                break;
            }



        }
        System.out.println("\n Answer: ");
        int sumTurnAroundTime = 0, sumWaitingTime = 0;
        for(int j = 0; j < processesList.size(); j++){
            System.out.println("Process Name: " + processesList.get(j).processName);
            System.out.println("Waiting Time: " + processesList.get(j).waitingTime);
            System.out.println("Turnaround Time: " + processesList.get(j).turnAroundTime);
            sumWaitingTime += processesList.get(j).waitingTime;
            sumTurnAroundTime += processesList.get(j).turnAroundTime;
            System.out.println();
        }
        double averageTurnAroundTime = (double) sumTurnAroundTime / processesList.size();
        double averageWaitingTime = (double) sumWaitingTime / processesList.size();
        System.out.println("Current time(Finished at time) in RR : " + curTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    }

}

//2
//5
//3
//1
//p1
//0
//4
//1
//p2
//1
//8
//1
//p3
//3
//2
//1
//p4
//10
//6
//1
//p5
//12
//5
//1




//2
//    1
//    2
//    1
//    p1
//    0
//    20




//2
//4
//3
//1
//p1
//0
//17
//4
//p2
//2
//6
//7
//p3
//5
//11
//3
//p4
//15
//4
//6

