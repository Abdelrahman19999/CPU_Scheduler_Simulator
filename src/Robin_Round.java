import java.util.Vector;

public class Robin_Round extends Scheduler{
    
	
	double Quantum , Context_Time ;

	public void Add_Processes(){
     
	 process = new Vector<Process>() ;	
	 
	 order = new Vector<String>() ;
		
     System.out.println("Enter Number Of Processes");
     
     Number_Of_Processes = input.nextDouble() ;

    
     System.out.println("Enter Quantum Time");

     Quantum=input.nextDouble() ;

     System.out.println("Enter Context Time"); 

     Context_Time=input.nextDouble() ; ;

     for(int i=1;i<=Number_Of_Processes;i++){

          String name;

          double arrive,burst;

          System.out.println("Enter Process " + i) ;

          System.out.print("Name : ") ;

          name = input.next() ;

          System.out.print("Arrival Time : ") ;

          arrive = input.nextDouble();

          System.out.print("Burst Time : ");

          burst = input.nextDouble();

          Process p = new Process(name,arrive,burst); ;
          
          process.add(p);

          System.out.println();
        }

    }



 public void Run_Processes(){

    boolean Finish = false ;

    while(!Finish){

     Finish = true;

     for(int i=0;i<Number_Of_Processes;i++){

        if(!process.elementAt(i).Done){

            order.add(process.elementAt(i).Name);

            Finish = false;

            if(Time!=0)Time+=Context_Time;

            if(process.elementAt(i).Arrival_Time>Time)Time=process.elementAt(i).Arrival_Time;

            if(process.elementAt(i).Current_Burst_Time<process.elementAt(i).Burst_Time){

                 if(process.elementAt(i).Burst_Time-process.elementAt(i).Current_Burst_Time > Quantum){

                    process.elementAt(i).Current_Burst_Time+=Quantum;

                    Time+=Quantum;

                }
                 else {

                    Time += process.elementAt(i).Burst_Time-process.elementAt(i).Current_Burst_Time;

                    process.elementAt(i).Current_Burst_Time += process.elementAt(i).Burst_Time-process.elementAt(i).Current_Burst_Time;

                    process.elementAt(i).Done=true;

                    process.elementAt(i).Set_Turnaround_Time(Time-process.elementAt(i).Arrival_Time);

                    process.elementAt(i).Set_Waiting_Time(Time-process.elementAt(i).Arrival_Time-process.elementAt(i).Burst_Time);
                 }

              }

          }


     }

   }

 }


 public void View_WorkDone(){

     for(int i=0;i<Number_Of_Processes;i++){
       
    	 
        if(i==0)System.out.println("Name   Turnaround Time     Waiting Time");

        System.out.println(process.elementAt(i).Name + "        " + process.elementAt(i).Turnaround_Time + "                   " + process.elementAt(i).Waiting_Time);

     }
     
     
     System.out.println("AVG :     " + AVG_Turnarround_Time + "                   " +  AVG_Waiting_Time);
     
     System.out.println("\nProcesses Execution Order\n\n");
     
     for(String e : order)System.out.print(e + "  ") ;

     System.out.println() ;
  }


 public void Calculate_AVG(){

   double Sum=0;

   for(Process e : process)Sum+=e.Waiting_Time;

   AVG_Waiting_Time = Sum/Number_Of_Processes;

   Sum=0;

   for(Process e : process)Sum+=e.Turnaround_Time;

   AVG_Turnarround_Time = Sum/Number_Of_Processes;

  }

	
}
