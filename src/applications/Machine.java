package applications;

import dataStructures.LinkedQueue;

public class Machine {
    // data members
    LinkedQueue jobQ; // queue of waiting jobs for this machine
    int changeTime; // machine change-over time
    int totalWait; // total delay at this machine
    int numTasks; // number of tasks processed on this machine
    Job activeJob; // job currently active on this machine

    // constructor
    public Machine() {
        jobQ = new LinkedQueue();
    }
    
    // tells the jobQ is empty or not.
    public boolean jobQIsEmpty(){
    	return jobQ.isEmpty();
    }
    
    // 
    public int workOnJob() {
		activeJob = (Job) jobQ.remove();
		totalWait += MachineShopSimulator.getCurrentTime() - activeJob.arrivalTime;
		numTasks++;
		return activeJob.removeNextTask();
	}
    public Job getActiveJob(){
    	return activeJob;
    }

	public void clearActiveJob() {
		activeJob = null;
		
	}
}
