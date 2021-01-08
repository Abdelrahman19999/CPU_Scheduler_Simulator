package Schedulers;
import java.util.Scanner;
import Process.Process;

public class FirstComeFirstServed implements IScheduler {
	private Scanner sc;
	
    void calculateWaitingTime(Process process){
        process.waitingTime = process.turnaroundTime - process.getBurstTime();
    }
    
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
    public void schedule(){
    	System.out.println("Enter the number of processes: ");
    	Scanner sc=new Scanner(System.in);
    	int pCount = sc.nextInt();
    	sc.nextLine();
    	Process[] process = new Process[pCount];
    	for(int i = 0; i < pCount; i++)
    	{
    		System.out.println("Enter process " + (i+1) + "'s name: ");
    		String pName = sc.nextLine();
    		System.out.println("Enter process " + (i+1) + "'s arrival time: ");
    		int pArrivalTime = sc.nextInt();
    		System.out.println("Enter process " + (i+1) + "'s burst time: ");
    		int pBurstTime = sc.nextInt();
    		sc.nextLine();
    		process[i] = new Process(pArrivalTime, pBurstTime, pName);
    		
    	}

        int time = 1;
        for (int i=0;i<process.length;i++)
    	{
    		for (int j=0;j<process.length;j++)
    		{
    			if (process[i].getArrivalTime()<process[j].getArrivalTime())
    			{
    				Process temp=process[i];
    				process[i]=process[j];
    				process[j]=temp;
    			}
    		}
    	}
        int totalExecutionTime = calculateTotalExecutionTime(process);
        for ( int i = 0,j = 1; j < totalExecutionTime && i < process.length; i++){
        	int exect=process[i].getBurstTime();
        	while (process[i].getArrivalTime()>j)
        	{
        		System.out.println("Time:"+time+"     "+"Arrival Time:"+process[i].getArrivalTime());
        		System.out.println("Process: NULL ||| Time: " + time);
                System.out.println("-------------------------------------");
                j++;
                time = (time + 1);
        	}
        	while (exect>0)
        	{
        		System.out.println("Process: " + process[i].processName + " ||| Time: " + time);
                System.out.println("-------------------------------------");
                process[i].executionTimeTEMP--;
                j++; //if context switch happened, then: time+=contextTime;
                time = (time + 1);
                calculateTurnaroundTime(process[i], time);
                exect--;
        	}
            
        }
        for (int i=0; i < process.length; i++){
            System.out.println("Process " + process[i].processName + " stats: ");
            System.out.println("Waiting time: " + process[i].waitingTime);
            System.out.println("Turnaround time: " + process[i].turnaroundTime);
        }
    }
    
}
