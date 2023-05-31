import java.util.ArrayList;
import java.util.HashMap;

public class WorstFit {
    //    ArrayList<String>allocation; //hold process names that are allocated
    HashMap<Pair, String> allocation;
    ArrayList<Boolean> isAllocated;
    ArrayList<Pair> externalIndices;
    ArrayList<Pair> partitions, processes;

    int lastName;
    WorstFit(ArrayList<Pair> partitions, ArrayList<Pair> processes) {
        this.partitions = partitions;
        this.processes = processes;
        allocation = new HashMap<>();
        isAllocated = new ArrayList<>();
        for(int i = 0; i < processes.size(); i++) isAllocated.add(false);
        externalIndices = new ArrayList<>();
        lastName = partitions.size();
    }
    void compact(){
        Pair externalPartition = new Pair();
        externalPartition.name = "Partition-" + lastName;
        externalPartition.size = 0;
        ArrayList<Pair> externalIndices = new ArrayList<>();
        for (int i = 0; i < partitions.size(); i++){
            if(allocation.containsKey(partitions.get(i)))continue;
            externalIndices.add(partitions.get(i));
            externalPartition.size += partitions.get(i).size;
        }
        for(int i = 0; i < externalIndices.size(); i++){
            partitions.remove(externalIndices.get(i));
        }
        for (int i = 0; i < processes.size(); i++) {
            if (isAllocated.get(i)) continue;
            if(processes.get(i).size > externalPartition.size)continue;
            externalPartition.size -= processes.get(i).size;
            String processName = processes.get(i).name;
            int processSize = processes.get(i).size;
            Pair partition = new Pair();
            partition.name = "Partition-" + lastName++;
            partition.size = processSize;
            partitions.add(partition);
            allocation.put(partition, processName);
            isAllocated.set(i, true);
        }
        if(externalPartition.size > 0){
            externalPartition.name = "Partition-" + lastName++;
            partitions.add(externalPartition);
        }
    }
    void print(){
        //printing
        for(int i=0 ; i<partitions.size() ; i++){
            if (allocation.containsKey(partitions.get(i))) //process inside
                System.out.println(partitions.get(i).name + '(' + partitions.get(i).size + " KB) => " + allocation.get( partitions.get(i) )  );
            else{
                System.out.println(partitions.get(i).name + '(' + partitions.get(i).size + " KB) => External Fragment");
            }
        }
        System.out.println();
        for(int i=0 ;i<processes.size() ; i++){
            if(!isAllocated.get(i)){
                System.out.println(processes.get(i).name + " can not be allocated.");
            }
        }
    }
    void worstFit(){
        for(int i = 0; i < processes.size(); i++){
            String processName = processes.get(i).name;
            int processSize = processes.get(i).size;
            Pair maxSuitablePartition = null;
            int indexMaxPartition = -1;
            for(int j = 0; j < partitions.size(); j++){
                if (allocation.containsKey(partitions.get(j)) || processSize > partitions.get(j).size)continue;
                if(maxSuitablePartition != null && partitions.get(j).size <= maxSuitablePartition.size)continue;
                maxSuitablePartition = partitions.get(j);
                indexMaxPartition = j;
            }
            //can not be allocated
            if(maxSuitablePartition == null){
                continue;
            }
            allocation.put(partitions.get(indexMaxPartition), processName);
            isAllocated.set(i, true);
            int remain = partitions.get(indexMaxPartition).size - processSize;
            partitions.get(indexMaxPartition).size = processSize;
            if(remain == 0)continue;
            Pair partition = new Pair();
            partition.name = "Partition-" + lastName++;
            partition.size = remain;
            partitions.add(indexMaxPartition + 1, partition);
        }
    }
}
