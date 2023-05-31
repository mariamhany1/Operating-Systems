import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Pair{
    String name;
    int size;
    Pair(){}
    Pair(String name, int size){
        this.name = name;
        this.size = size;
    }
}

public class main {
    public static void main(String[] args) {
        ArrayList<Pair>partitions=new ArrayList<>();
        ArrayList<Pair>processes=new ArrayList<>();
        //Fill Partitions
        System.out.println("Enter number of partitions:");
        int partitionNumber , partitionSize;
        String partitionName;
        Scanner sc = new Scanner(System.in);
        partitionNumber = sc.nextInt();
        for (int i=0 ; i<partitionNumber ; i++)
        {
            System.out.println("Partition #" + (i + 1));
            System.out.println("Partition name: ");
            Pair partition = new Pair();
            partition.name = sc.next();
            System.out.println("Partition size: ");
            partition.size = sc.nextInt();
            partitions.add(partition);
        }
        //Fill processes
        System.out.println("Enter number of processes:");
        int processesNumber , processSize;
        String processName;
        processesNumber = sc.nextInt();
        for (int i=0 ; i<processesNumber ; i++)
        {

            System.out.println("Process #" + (i + 1));
            System.out.println("Process name: ");
            Pair process = new Pair();
            process.name = sc.next();
            System.out.println("Process size: ");
            process.size = sc.nextInt();
            processes.add(process);
        }
        System.out.println("Select the policy you want to apply:");
        System.out.println("1. First fit");
        System.out.println("2. Worst fit");
        System.out.println("3. Best fit");
        int selection;
        selection = sc.nextInt();
        if(selection==1) //First Fit
        {
            FirstFit firstFit = new FirstFit(partitions, processes);
            firstFit.firstFit();
            firstFit.print();
            System.out.println('\n');
            System.out.println("Do you want to compact");
            System.out.println("1) YES");
            System.out.println("2) NO");
            int compact = sc.nextInt();
            if(compact == 1){
                firstFit.compact();
                firstFit.print();
            }
        }
        else if (selection == 2){
            WorstFit worstFit = new WorstFit(partitions, processes);
            worstFit.worstFit();
            worstFit.print();
            System.out.println("Do you want to compact");
            System.out.println("1) YES");
            System.out.println("2) NO");
            System.out.println('\n');
            int compact = sc.nextInt();
            if(compact == 1){
                worstFit.compact();
                worstFit.print();
            }
        }
        else if(selection == 3){
            BestFit bestFit = new BestFit(partitions, processes);
            bestFit.bestFit();
            bestFit.print();
            System.out.println('\n');
            System.out.println("Do you want to compact");
            System.out.println("1) YES");
            System.out.println("2) NO");
            int compact = sc.nextInt();
            if(compact == 1){
                bestFit.compact();
                bestFit.print();
            }
        }
    }
}

/*
6
Partition-0 90
Partition-1 20
Partition-2 5
Partition-3 30
Partition-4 120
Partition-5 80
4
Process-1 15
Process-2 90
Process-3 30
Process-4 100
3
1
 */
