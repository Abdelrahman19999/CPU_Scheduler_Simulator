package Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import Process.Process;

public class Round_Robin implements IScheduler {

	int Quantum, Context_Time;

	Scanner input = new Scanner(System.in);

	ArrayList<Process> process;

	ArrayList<String> order;

	int Time = 0, Number_Of_Processes;

	double AVG_Turnarround_Time = 0, AVG_Waiting_Time = 0;

	public void Add_Processes() {

		process = new ArrayList<Process>();

		order = new ArrayList<String>();

		System.out.println("Enter Number Of Processes");

		Number_Of_Processes = input.nextInt();

		System.out.println("Enter Quantum Time");

		Quantum = input.nextInt();

		System.out.println("Enter Context Time");

		Context_Time = input.nextInt();

		for (int i = 1; i <= Number_Of_Processes; i++) {

			String name;

			int arrive, burst;

			System.out.println("Enter Process " + i);

			System.out.print("Name : ");

			name = input.next();

			System.out.print("Arrival Time : ");

			arrive = input.nextInt();

			System.out.print("Burst Time : ");

			burst = input.nextInt();

			Process p;

			p = new Process(arrive, burst, name);

			process.add(p);

			System.out.println();
		}

	}

	public void Run_Processes() {

		boolean Finish = false;
		
		boolean context = false;

		int tmp = -1 ;
		
		while (!Finish) {

			Finish = true;
           
			if(tmp==Time)Time++;
				
			tmp=Time;
			
		
			for (int i = 0; i < Number_Of_Processes; i++) {

				if (!process.get(i).Done) {

					if (process.get(i).getArrivalTime() > Time) {Finish= false; continue ;}
					
					Finish = false;

					if (context)Time += Context_Time;
					
					order.add(process.get(i).processName);
                     
					context = true ;
					
					if (process.get(i).Current_Burst_Time < process.get(i).getBurstTime()) {

						if (process.get(i).getBurstTime() - process.get(i).Current_Burst_Time > Quantum) {

							process.get(i).Current_Burst_Time += Quantum;

							Time += Quantum;

						} else {

							Time += process.get(i).getBurstTime() - process.get(i).Current_Burst_Time;

							process.get(i).Current_Burst_Time += process.get(i).getBurstTime()
									- process.get(i).Current_Burst_Time;

							process.get(i).Done = true;

							process.get(i).turnaroundTime = (Time - process.get(i).getArrivalTime());

							process.get(i).waitingTime = (Time - process.get(i).getArrivalTime()
									- process.get(i).getBurstTime());
						}

					}

				}

			}

		}

	}

	public void View_WorkDone() {

		for (int i = 0; i < Number_Of_Processes; i++) {

			if (i == 0)
				System.out.println("Name   Turnaround Time     Waiting Time");

			System.out.println(process.get(i).processName + "        " + process.get(i).turnaroundTime
					+ "                   " + process.get(i).waitingTime);

		}

		System.out.println("AVG :     " + AVG_Turnarround_Time + "                 " + AVG_Waiting_Time);

		System.out.println("\nProcesses Execution Order\n\n");

		for (String e : order)
			System.out.print(e + "  ");

		System.out.println();
	}

	public void Calculate_AVG() {

		double Sum = 0;

		for (Process e : process)
			Sum += e.waitingTime;

		AVG_Waiting_Time = Sum / Number_Of_Processes;

		Sum = 0;

		for (Process e : process)
			Sum += e.turnaroundTime;

		AVG_Turnarround_Time = Sum / Number_Of_Processes;

	}

    

	public void schedule() {

		Add_Processes();
		Run_Processes();
		Calculate_AVG();
		View_WorkDone();

	}

}
