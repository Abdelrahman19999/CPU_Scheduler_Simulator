import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Priority implements IScheduler {
	private ArrayList<Process> processes = new ArrayList<Process>();
	private ArrayList<Process> completed = new ArrayList<Process>();
	private ArrayList<Process> sequence = new ArrayList<Process>();
	
	private Scanner sc = new Scanner(System.in);
	private Scanner scLine = new Scanner(System.in);
	
	private int pCount;
	private int pArrivalTime;
	private int pBurstTime;
	private int pPriority;
	private String pName;
	
	private double cummWait = 0.0;
	private double cummTurn = 0.0;
	
	Comparator<Process> comparator = new Comparator<Process>() {
	    @Override
	    public int compare(Process p1, Process p2) {
	        Integer i1 = new Integer(p1.getArrivalTime());
	        Integer i2 = new Integer(p2.getArrivalTime());
	        return i1.compareTo(i2);    
	    }
	};
	
	@Override
	public void schedule()
	{
		System.out.println("Enter the number of processes: ");
		pCount = sc.nextInt();
		for(int i = 0; i < pCount; i++)
		{
			System.out.println("Enter process " + (i+1) + "'s name: ");
			pName = scLine.nextLine();
			System.out.println("Enter process " + (i+1) + "'s arrival time: ");
			pArrivalTime = sc.nextInt();
			System.out.println("Enter process " + (i+1) + "'s burst time: ");
			pBurstTime = sc.nextInt();
			System.out.println("Enter process " + (i+1) + "'s priority: ");
			pPriority = sc.nextInt();
			Process p = new Process(pArrivalTime, pBurstTime, pName);
			p.priority = pPriority;
			p.waitingTime = 0;
			processes.add(p);
			
		}
		
		Collections.sort(processes, comparator);
		
		int time = 0;

		do
		{
			Process executing = processes.get(0);
			int index = 0;
			for(int i = 1; i < pCount; i++)
			{
				Process p = processes.get(i);
				if(time >= p.getArrivalTime())
				{
					if(p.priority < executing.priority)
					{
						executing.waitingTime++;
						executing = p;
						index = i;
					}
					else
					{
						p.waitingTime++;
						if(p.waitingTime % 5 == 0) p.priority--; // Solving starvation problem
					}
				}
				else break;
			}
			sequence.add(executing);
			executing.executionTimeTEMP--;
			if(executing.executionTimeTEMP == 0) 
			{
				completed.add(processes.get(index));
				processes.remove(index);
				pCount--;
			}
			time++;
		}
		while(pCount > 0);
		
		System.out.println("\nExecution Timeline: ");
		System.out.println("Process Name         Time");
		for(int i = 0; i < sequence.size(); i++)
		{
			System.out.println(sequence.get(i).processName + "                    " + (i));
		}
		System.out.println("\n\nWaiting Times and Turnaround Times: ");
		for(int i = 0; i < completed.size(); i++)
		{
			int wTime = completed.get(i).waitingTime;
			int taTime = wTime + completed.get(i).getBurstTime();
			cummWait += wTime;
			cummTurn += taTime;
			System.out.println(completed.get(i).processName + "               " + wTime + "               " + taTime);
		}
		
		System.out.println("\n\nAverage waiting time: ");
		System.out.println(cummWait/(double)completed.size());
		
		System.out.println("\n\nAverage turnaround time: ");
		System.out.println(cummTurn/(double)completed.size());
	}

}