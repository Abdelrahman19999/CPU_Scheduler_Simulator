package Process;

public class Process {    
    private int arrivalTime;
    private int burstTime;
    public String processName;
    public int waitingTime;
    public int turnaroundTime;
    public int executionTimeTEMP; //Used for operations instead of burst time; Like decrementing and incrementing
    public int priority;
    public int Current_Burst_Time;
    public boolean Done ;
    
    public Process() {};
    public Process(int arrivalTime, int burstTime, String processName){
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.processName = processName;
        executionTimeTEMP = burstTime;
        Current_Burst_Time = 0 ; // Used to measure the achieved burst time of the process 
        Done = false ; // checks if the process terminated or not 
    }

    public void resetExecutionTimeTEMP(){executionTimeTEMP = burstTime;}
    
    public int getBurstTime(){
        return this.burstTime;
    }
    
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    
}
