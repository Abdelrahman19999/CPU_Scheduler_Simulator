package Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Process.Process;

public class Multi_Scheduler implements IScheduler {

	ArrayList<Process> RR;

	ArrayList<Process> FCFS;

	ArrayList<Integer> time;

	int Quantum ;

	Scanner input = new Scanner(System.in);

	int Time = 0, Number_Of_Processes;

	double AVG_Turnarround_Time = 0, AVG_Waiting_Time = 0;

	ArrayList<String> order;



	public void schedule() {

		int tmp = -1;

		Add_Processes();

		Sorting_FCFS();

		while (Check_RR())
			Run_RR();

		while (!Finish_FCFS() || !Finish_RR()) {


			if(Check_RR())Run_RR();
			else Run_FCFS();

			if (Time == tmp)
				Time++;

			tmp = Time;

		}

		time.add(Time);

		Calculate_AVG();

		View_WorkDone();

	}

	public void Add_Processes() {

		int Queue_Number;

		RR = new ArrayList<Process>();

		FCFS = new ArrayList<Process>();

		order = new ArrayList<String>();

		time = new ArrayList<>();

		System.out.println("Enter Number Of Processes");

		Number_Of_Processes = input.nextInt();

		System.out.println("Enter Quantum Time");

		Quantum = input.nextInt();


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

			System.out.print("Queue Number : ");

			Queue_Number = input.nextInt();

			Process p;

			p = new Process(arrive, burst, name);

			if (Queue_Number == 1)
				RR.add(p);
			else
				FCFS.add(p);

			System.out.println();
		}

	}

	public void Run_FCFS() {

		for (Process e : FCFS) {

			if (!e.Done) {

				if (e.getArrivalTime() > Time)
					return;

				order.add(e.processName);
				time.add(Time);

				while (e.Current_Burst_Time < e.getBurstTime()) {

					e.Current_Burst_Time++;
					Time++;

					boolean yes = false;

					if (Check_RR())
						yes = true;

					while (Check_RR())
						Run_RR();

					if (yes) {

						order.add(e.processName);

						time.add(Time);

						yes = false;

					}

				}

				if (e.Current_Burst_Time == e.getBurstTime()) {

					e.Done = true;

					e.turnaroundTime = (Time - e.getArrivalTime());

					e.waitingTime = (Time - e.getArrivalTime() - e.getBurstTime());

				}

			}

		}

	}

	public void Run_RR() {

		for (int i = 0; i < RR.size(); i++) {

			if (!RR.get(i).Done) {

				if (RR.get(i).getArrivalTime() > Time)continue;

				order.add(RR.get(i).processName);
				time.add(Time);


				if (RR.get(i).Current_Burst_Time < RR.get(i).getBurstTime()) {

					if (RR.get(i).getBurstTime() - RR.get(i).Current_Burst_Time > Quantum) {

						RR.get(i).Current_Burst_Time += Quantum;

						Time += Quantum;

					} else {

						Time += RR.get(i).getBurstTime() - RR.get(i).Current_Burst_Time;

						RR.get(i).Current_Burst_Time += RR.get(i).getBurstTime() - RR.get(i).Current_Burst_Time;

						RR.get(i).Done = true;

						RR.get(i).turnaroundTime = (Time - RR.get(i).getArrivalTime());

						RR.get(i).waitingTime = (Time - RR.get(i).getArrivalTime() - RR.get(i).getBurstTime());
					}

				}

			}

		}

	}

	public boolean Finish_FCFS() {

		for (Process e : FCFS) {
			if (!e.Done)
				return false;

		}
		return true;
	}

	public boolean Finish_RR() {

		for (Process e : RR) {
			if (!e.Done)
				return false;

		}
		return true;
	}

	public boolean Check_RR() {

		if (Finish_RR())
			return false;

		for (Process e : RR) {
			if (!e.Done && e.getArrivalTime() <= Time)
				return true;

		}
		return false;
	}

	public void View_WorkDone() {

		for (int i = 0; i < RR.size(); i++) {

			if (i == 0)System.out.println("Name   Turnaround Time     Waiting Time");

			System.out.println(RR.get(i).processName + "        " + RR.get(i).turnaroundTime + "                   "
					+ RR.get(i).waitingTime);

		}

		if (RR.size()==0)System.out.println("Name   Turnaround Time     Waiting Time");

		for (int i = 0; i < FCFS.size(); i++) {

			System.out.println(FCFS.get(i).processName + "        " + FCFS.get(i).turnaroundTime + "                   "
					+ FCFS.get(i).waitingTime);

		}

		System.out.println("AVG :     " + AVG_Turnarround_Time + "                 " + AVG_Waiting_Time);

		System.out.println("\nProcesses Execution Order\n\n");

		for (String e : order)
			System.out.print(e + "  ");

		System.out.println();

	}

	public void swap_FCFS(int i, int j) {

		Collections.swap(FCFS, i, j);

	}

	public void swap_RR(int i, int j) {

		Collections.swap(RR, i, j);

	}

	public void Sorting_RR() {

		for (int i = 0; i < RR.size(); i++)
			for (int j = 0; j < RR.size(); j++) {
				Process x = RR.get(i);
				Process y = RR.get(j);
				if (x.getArrivalTime() < y.getArrivalTime())
					swap_RR(i, j);

			}

	}

	public void Sorting_FCFS() {

		for (int i = 0; i < FCFS.size(); i++)
			for (int j = 0; j < FCFS.size(); j++) {
				Process x = FCFS.get(i);
				Process y = FCFS.get(j);
				if (y.getArrivalTime() > x.getArrivalTime())
					swap_FCFS(i, j);
				if (y.getArrivalTime() == x.getArrivalTime()) {
					if (y.getBurstTime() > x.getBurstTime())
						swap_FCFS(i, j);
				}
			}

	}

	public void Calculate_AVG() {

		double Sum = 0;

		for (Process e : RR)
			Sum += e.waitingTime;

		for (Process e : FCFS)
			Sum += e.waitingTime;

		AVG_Waiting_Time = Sum / Number_Of_Processes;

		Sum = 0;

		for (Process e : RR)
			Sum += e.turnaroundTime;

		for (Process e : FCFS)
			Sum += e.turnaroundTime;

		AVG_Turnarround_Time = Sum / Number_Of_Processes;

	}

}
