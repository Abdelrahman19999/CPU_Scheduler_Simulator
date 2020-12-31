
import java.util.Scanner;


public class ShortestJobFirst {
    void calculateWaitingTime(Process process){
        process.waitingTime = process.turnaroundTime - process.getBurstTime();
    }
    /*
    void calculateWaitingTime(Process process, int startTime){
        if (process.state == true){
            process.waitingTime = startTime - process.arrivalTime;
        }
        process.state = false;
    }*/
    void calculateTurnaroundTime(Process process, int time){
        if (process.executionTimeTEMP <= 0){
        process.turnaroundTime = time - process.getArrivalTime();
        calculateWaitingTime(process);
        }
    }
    
    float calculateWaitingTimeAVG(Process[] process){
        float average = 0;
        for (int i=0; i < process.length; i++){
            average+= process[i].waitingTime;
        }
        average/= process.length;
        return average;
    }
    float calculateTurnaroundTimeAVG(Process[] process){
        float average = 0;
        for (int i = 0; i < process.length; i++){
            average+= process[i].turnaroundTime;
        }
        average/= process.length;
        return average;
    }
    int calculateTotalExecutionTime(Process[] process){
        int totalExecutionTime = 0;
        for (int i = 0; i < process.length; i++){
            totalExecutionTime+= process[i].getBurstTime();
        }
        return totalExecutionTime;
    }
    //Preemptive scheduling
    void scheduleSFJ(Process[] process, int contextTime){
        int time = 0;
        int totalExecutionTime = calculateTotalExecutionTime(process);
        Process[] contextProcess = new Process[2];
        for ( int i = 0; totalExecutionTime > 0; i++){
            if (i >= process.length){i=0;}
            if (process[i].getArrivalTime() > time){continue;}
            if (process[i].executionTimeTEMP <= 0){continue;}
            for (int j = 0; j < process.length; j++){
//                System.out.println("I: " + i + " J: " + j);
                 if (process[j].executionTimeTEMP <= 0){continue;}
                 if (process[i].getArrivalTime() <= time && process[j].getArrivalTime() > time){ //Case 1
                    continue;
                } /*Case 2; ( P[i] & P[i-1] )'s arrival time is less than or equal to time,
                        then we compare their execution time */
                else if (process[i].getArrivalTime() <= time && process[j].getArrivalTime() <= time){
                    if (process[i].executionTimeTEMP <= process[j].executionTimeTEMP){
                        continue;
                    }else{
                        i = j;
                    }
                }
            }
//            calculateWaitingTime(process[i], time);
            contextProcess[1] = process[i]; //Saving our Current process
            System.out.println("Process: " + process[i].processName + " ||| Time: " + time);
            System.out.println("-------------------------------------");
            process[i].executionTimeTEMP--;
            totalExecutionTime--; //if context switch happened, then: time+=contextTime;
            time = contextProcess[0]!=null &&
                    contextProcess[0].processName != contextProcess[1].processName ||
                                                    process[i].executionTimeTEMP == 0? (time + contextTime + 1) : (time + 1);
            calculateTurnaroundTime(process[i], time);
            contextProcess[0] = process[i]; //Current process will be previous process in the next loop
        }
    }
    public static void main(String[] args){
//        Scanner scan = new Scanner(System.in);
//        int contextTime = scan.nextInt();
//        Process[] process = new Process[5];
//        for (int i=0 ; i < 5; i++){
//            process[i] = new Process();
//            String processName = scan.next();
//            process[i].processName = processName;
//            int arrivalTime = scan.nextInt();
//            process[i].arrivalTime = arrivalTime;
//            int executionTime = scan.nextInt();
//            process[i].executionTime = executionTime;
//        }
        int contextTime = 0; 
        Process[] process = new Process[7];
        process[0] = new Process(0,1,"P1");
        process[1] = new Process(1,7,"P2");
        process[2] = new Process(2,3,"P3");
        process[3] = new Process(3,6,"P4");
        process[4] = new Process(4,5,"P5");
        process[5] = new Process(5,15,"P6");
        process[6] = new Process(15,8,"P7");
//          process[0] = new Process(0,4, "P1");
//          process[1] = new Process(1,8, "P2");
//          process[2] = new Process(3,2, "P3");
//          process[3] = new Process(10, 12, "P4");
//          process[4] = new Process(12, 5, "P5");
        ShortestJobFirst sfj = new ShortestJobFirst();
        sfj.scheduleSFJ(process, contextTime);
        for (int i=0; i < process.length; i++){
            System.out.println("Process " + process[i].processName + " stats: ");
            System.out.println("Waiting time: " + process[i].waitingTime);
            System.out.println("Turnaround time: " + process[i].turnaroundTime);
        }
    }
}
