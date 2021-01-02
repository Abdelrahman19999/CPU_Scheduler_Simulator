public class Process {

	 double Arrival_Time , Burst_Time , Current_Burst_Time=0 , Waiting_Time  , Turnaround_Time ;

	   boolean Done ;

	   String Name ;

	   Process(String name , double Arrive , double Burst){

	       Arrival_Time = Arrive ;

	       Burst_Time   = Burst ;

	       Name         = name ;

	       Done         = false ;
	    }

	   void Set_Waiting_Time(double Time){

	       Waiting_Time=Time ;

	   }

	   void Set_Turnaround_Time(double Time){

	       Turnaround_Time=Time ;

	   }

	
}
