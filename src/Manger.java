
public class Manger {

	public static void main(String[] args) {
		
		Scheduler obj = new Robin_Round() ;
		obj.Add_Processes();
		obj.Run_Processes();
		obj.Calculate_AVG();
		obj.View_WorkDone();

	}

}
