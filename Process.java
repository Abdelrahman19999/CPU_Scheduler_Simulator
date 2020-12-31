public class Process {    
    private int arrivalTime;
    private int burstTime;
    protected String processName;
    protected int waitingTime;
    protected int turnaroundTime;
    protected int executionTimeTEMP; //Used for operations instead of burst time; Like decrementing and incrementing
    //protected boolean state; //Waiting state
    Process(int arrivalTime, int burstTime, String processName){
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.processName = processName;
        executionTimeTEMP = burstTime;
//        state = true;
    }
//    Process(){state = true;}
    void resetExecutionTimeTEMP(){executionTimeTEMP = burstTime;}
    int getBurstTime(){
        return this.burstTime;
    }
    int getArrivalTime(){
        return this.arrivalTime;
    }
    
}
