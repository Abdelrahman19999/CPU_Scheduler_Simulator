package Schedulers;

import java.util.ArrayList;
import java.util.Scanner;

import Process.Process;
import Process.Process;

public class Multi_Scheduler implements IScheduler{
   
	public void schedule() {}
   
	ArrayList<Process>RR ;
	
	ArrayList<Process>FCFS ;
	
	int Quantum , Context_Time ;
    
    Scanner input= new Scanner(System.in);
    
    int Time = 0 , Number_Of_Processes ;
	
    double AVG_Turnarround_Time=0, AVG_Waiting_Time=0 ;
    
    ArrayList<String>order ;
    
    public void Add_Processes(){
        
    	int q_number ;
    	
       RR = new ArrayList<Process>() ;
       
       FCFS = new ArrayList<Process>() ;
		
	  order = new ArrayList<String>() ;
   		
        System.out.println("Enter Number Of Processes");
        
        Number_Of_Processes = input.nextInt() ;

       
        System.out.println("Enter Quantum Time");

        Quantum=input.nextInt() ;

        System.out.println("Enter Context Time"); 

        Context_Time=input.nextInt() ; ;

        for(int i=1;i<=Number_Of_Processes;i++){

             String name;

             int arrive,burst;

             System.out.println("Enter Process " + i) ;

             System.out.print("Name : ") ;

             name = input.next() ;

             System.out.print("Arrival Time : ") ;

             arrive = input.nextInt();

             System.out.print("Burst Time : ");

             burst = input.nextInt();
             
             System.out.print("Queue Number : ") ;

             q_number = input.nextInt();

             Process p ;
             
             p = new Process(arrive , burst , name) ;
             
             if(q_number==1)RR.add(p);
             else FCFS.add(p);

             System.out.println();
           }

       }
	
	public void Run_RR(){

		
	    boolean Finish = false ;
	    
	    int tmp = -1 ;

	    while(!Finish){

	     Finish = true;

	     if(tmp==Time)Time++;

	     tmp = Time;
	     
	     for(int i=0;i<Number_Of_Processes;i++){

	        if(!RR.get(i).Done){

	        	if(RR.get(i).getArrivalTime()>Time){Finish=false; break;}
	        	
	            order.add(RR.get(i).processName);

	            Finish = false;

	            if(Time!=0)Time+=Context_Time;

	            

	            if(RR.get(i).Current_Burst_Time<RR.get(i).getBurstTime()){

	                 if(RR.get(i).getBurstTime()-RR.get(i).Current_Burst_Time > Quantum){

	                    RR.get(i).Current_Burst_Time+=Quantum;

	                    Time+=Quantum;

	                }
	                 else {

	                    Time += RR.get(i).getBurstTime()-RR.get(i).Current_Burst_Time;

	                    RR.get(i).Current_Burst_Time += RR.get(i).getBurstTime()-RR.get(i).Current_Burst_Time;

	                    RR.get(i).Done=true;

	                    RR.get(i).turnaroundTime = (Time-RR.get(i).getArrivalTime());

	                    RR.get(i).waitingTime = (Time-RR.get(i).getArrivalTime()-RR.get(i).getBurstTime());
	                 }

	              }

	          }


	     }

	   }
	 
	    
	    
	 }
	
	

}
