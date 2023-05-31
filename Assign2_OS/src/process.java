import java.util.Comparator;

class ProcessComparator implements Comparator<process> {
    public int compare(process p1, process p2) {
        if (p1.burstTime > p2.burstTime)
            return 1;
        else if (p1.burstTime == p2.burstTime){
            if(p1.arrivalTime > p2.arrivalTime){
                return 1;
            }
            else if(p1.arrivalTime < p2.arrivalTime){
                return -1;
            }
        }
        else{
            return -1;
        }
        return 0;
    }
}

class ProcessComparator2 implements Comparator<process> {
    @Override
    public int compare(process p1, process p2) {
        if(p1.priority > p2.priority){
            return 1;
        }
        else if(p1.priority == p2.priority){
            if(p1.arrivalTime > p2.arrivalTime){
                return 1;
            }
            else if(p1.arrivalTime == p2.arrivalTime){
                if(p1.burstTime > p2.burstTime){
                    return 1;
                }
                else if(p1.burstTime == p2.burstTime){
                    return 0;
                }
                else{
                    return -1;
                }
            }
            else{
                return -1;
            }
        }
        else{
            return -1;
        }
    }
}
public class process {
    String processName;
    int burstTime, arrivalTime, priority, quantum, quarterQuantum, completeTime;
    int waitingTime, turnAroundTime, originalExecution;

    process(String processName , int arrivalTime , int burstTime)
    {
        this.processName = processName;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.originalExecution = burstTime;
    }
    process(String processName , int arrivalTime , int burstTime, int priority)
    {
        this.processName = processName;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.originalExecution = burstTime;

    }
    process(String processName , int arrivalTime , int burstTime, int priority, int quantum)
    {
        this.processName = processName;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.quantum = quantum;
        quarterQuantum = (quantum + 3)/4;
        this.originalExecution = burstTime;
    }
}
