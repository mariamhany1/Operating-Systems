import java.util.*;


public class priorityScheduling {
    ArrayList<process> processesList;
    ArrayList<process> sortedProcess;
    HashMap<Integer, ArrayList<process>> compressedProcess;


    priorityScheduling(ArrayList<process> processesList){
        this.processesList = processesList;
        sortedProcess = new ArrayList<>();
        compressedProcess = new HashMap<>();
        for(int i = 0; i < processesList.size(); i++){
            if(!compressedProcess.containsKey(processesList.get(i).arrivalTime)){
                compressedProcess.put(processesList.get(i).arrivalTime, new ArrayList<>());
            }
            compressedProcess.get(processesList.get(i).arrivalTime).add(processesList.get(i));
        }
    }
    void priorityScheduling(){
        int curTime = 0, lastTime = 0;
        process curProcess = null;
        System.out.println("Order of Execution:");
        while (!compressedProcess.isEmpty() || !sortedProcess.isEmpty() || (curProcess != null && curProcess.burstTime > 0)){
            if(compressedProcess.containsKey(curTime)){
                for(int i = 0; i < compressedProcess.get(curTime).size(); i++){
                    sortedProcess.add(compressedProcess.get(curTime).get(i));
                }
                compressedProcess.remove(curTime);
            }
            if(!sortedProcess.isEmpty()) {
                sortedProcess.sort(new ProcessComparator2());
                if(curProcess == null){
                    curProcess = sortedProcess.get(0);
                }
                else{
                    if(!curProcess.processName.equals(sortedProcess.get(0).processName)){
                        System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                        curProcess.completeTime = curTime;
                        curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                        curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                        for(int i = 0; i < processesList.size();i++){
                            if(processesList.get(i).processName.equals(curProcess.processName)){
                                processesList.set(i, curProcess);
                                break;
                            }
                        }
                        lastTime = curTime;
                        curProcess = sortedProcess.get(0);
                    }
                }
                curProcess.burstTime--;
                if (curProcess.burstTime == 0) {
                    sortedProcess.remove(0);
                    if(compressedProcess.isEmpty() && sortedProcess.isEmpty()){
                        curTime++;
                        System.out.println("Process " + curProcess.processName + ": " + lastTime + " to " + curTime);
                        curProcess.completeTime = curTime;
                        curProcess.waitingTime = curProcess.completeTime - curProcess.arrivalTime - curProcess.originalExecution;
                        curProcess.turnAroundTime = curProcess.completeTime - curProcess.arrivalTime;
                        for(int i = 0; i < processesList.size();i++){
                            if(processesList.get(i).processName.equals(curProcess.processName)){
                                processesList.set(i, curProcess);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
//            if(!sortedProcess.isEmpty()) {
//                sortedProcess.get((int) sortedProcess.size() - 1).priority = Math.max(sortedProcess.get((int) (sortedProcess.size() - 1)).priority - 1, 0);
//                sortedProcess.sort(new ProcessComparator2());
//            }
            curTime++;
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
        System.out.println("Total Time Taken: " + (curTime));
        System.out.println("Average Waiting Time: " + averageWaitingTime);
        System.out.println("Average Turn Around Time: " + averageTurnAroundTime);
    }
}

//3
//5
//p1
//0
//8
//3
//p2
//1
//1
//1
//p3
//2
//3
//2
//p4
//3
//2
//3
//p5
//4
//6
//4
