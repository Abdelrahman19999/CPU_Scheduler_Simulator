package Schedulers;

import java.util.Scanner;
import Process.Process;
import static java.lang.Integer.min;

public class ShortestJobFirst implements IScheduler {
	private Scanner sc;
	
	void calculateWaitingTime(Process process) {
		process.waitingTime = process.turnaroundTime - process.getBurstTime();
	}

	void calculateTurnaroundTime(Process process, int time) {
		if (process.executionTimeTEMP <= 0) {
			process.turnaroundTime = time - process.getArrivalTime();
			calculateWaitingTime(process);
		}
	}

	float calculateWaitingTimeAVG(Process[] process) {
		float average = 0;
		for (int i = 0; i < process.length; i++) {
			average += process[i].waitingTime;
		}
		average /= process.length;
		return average;
	}

	float calculateTurnaroundTimeAVG(Process[] process) {
		float average = 0;
		for (int i = 0; i < process.length; i++) {
			average += process[i].turnaroundTime;
		}
		average /= process.length;
		return average;
	}

	int calculateTotalExecutionTime(Process[] process) {
		int totalExecutionTime = 0;
		for (int i = 0; i < process.length; i++) {
			totalExecutionTime += process[i].getBurstTime();
		}
		return totalExecutionTime;
	}

	// Gets the time of the first process to arrive
	int getFirstArrivalTime(Process[] process) {
		int time = process[0].getArrivalTime();
		for (int i = 1; i < process.length; i++) {
			if (time > process[i].getArrivalTime()) {
				time = process[i].getArrivalTime();
			}
		}
		return time;
	}

	// Preemptive scheduling
//    void schedule(Process[] process, int contextTime){
	public void schedule() {
		System.out.println("Enter the number of processes: ");
		sc = new Scanner(System.in);
		int pCount = sc.nextInt();
		sc.nextLine();
		Process[] process = new Process[pCount];
		for (int i = 0; i < pCount; i++) {
			System.out.println("Enter process " + (i + 1) + "'s name: ");
			String pName = sc.nextLine();
			System.out.println("Enter process " + (i + 1) + "'s arrival time: ");
			int pArrivalTime = sc.nextInt();
			System.out.println("Enter process " + (i + 1) + "'s burst time: ");
			int pBurstTime = sc.nextInt();
			sc.nextLine();
			process[i] = new Process(pArrivalTime, pBurstTime, pName);
		}
		System.out.println("Enter context time: ");
		int contextTime = sc.nextInt();
		sc.nextLine();
		int time = getFirstArrivalTime(process);
		int totalExecutionTime = calculateTotalExecutionTime(process);
		Process[] contextProcess = new Process[2];
		for (int i = 0; totalExecutionTime > 0; i++) {
			if (i >= process.length) {
				i = 0;
			}
			if (process[i].getArrivalTime() > time) {
				int minimum = process[i].getArrivalTime();
				boolean minimumFlag = true;
				for (int j = 0; j < process.length; j++) {
					if (process[j].executionTimeTEMP > 0 && process[j].getArrivalTime() > time
							&& minimumFlag != false) {
						minimum = min(process[j].getArrivalTime(), minimum);
						minimumFlag = true;
					} else if (process[j].executionTimeTEMP > 0 && process[j].getArrivalTime() <= time) {
						i = j;
						minimumFlag = false;
					}
				}
				minimum -= time;
				if (minimumFlag == true) {
					while (minimum > 0) {
						time++;
						minimum--;
					}
				}
//                continue;
			}
			if (process[i].executionTimeTEMP <= 0) {
				continue;
			}
			for (int j = 0; j < process.length; j++) {
				if (process[j].executionTimeTEMP <= 0) {
					continue;
				}
				if (process[i].getArrivalTime() <= time && process[j].getArrivalTime() > time) { // Case 1
					continue;
				} /*
					 * Case 2; ( P[i] & P[i-1] )'s arrival time is less than or equal to time, then
					 * we compare their execution time
					 */
				else if (process[i].getArrivalTime() <= time && process[j].getArrivalTime() <= time) {
					if (process[i].executionTimeTEMP <= process[j].executionTimeTEMP) {
						if (i <= j) {
							continue;
						} else {
							i = j;
						}
					} else {
						i = j;
					}
				}
			}
			contextProcess[1] = process[i]; // Saving our Current process
			System.out.println("Process: " + process[i].processName + " ||| Time: " + time);
			System.out.println("-------------------------------------");
			process[i].executionTimeTEMP--;
			totalExecutionTime--; // if context switch happened, then: time+=contextTime+1;
			time = contextProcess[0] != null && contextProcess[0].processName != contextProcess[1].processName
					&& !(contextProcess[0].executionTimeTEMP <= 0) || process[i].executionTimeTEMP <= 0
							? (time + contextTime + 1)
							: (time + 1);
			calculateTurnaroundTime(process[i], time);
			contextProcess[0] = process[i]; // Current process will be previous process in the next loop
		}
		for (int i = 0; i < process.length; i++) {
			System.out.println("Process " + process[i].processName + " stats: ");
			System.out.println("Waiting time: " + process[i].waitingTime);
			System.out.println("Turnaround time: " + process[i].turnaroundTime);
		}
		System.out.println("-------------------------------------");
		System.out.println("Average Waiting time: " + calculateWaitingTimeAVG(process));
		System.out.println("Average Turnaround time: " + calculateTurnaroundTimeAVG(process));
	}

	public static void main(String[] args) {
		ShortestJobFirst sjf = new ShortestJobFirst();
		sjf.schedule();
	}
}
