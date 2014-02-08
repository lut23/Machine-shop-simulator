package applications;

import dataStructures.LinkedQueue;

public class Machine {
    // data members
    private LinkedQueue jobQ; // queue of waiting jobs for this machine
    private int changeTime; // machine change-over time
    private int totalWait; // total delay at this machine
    int numTasks; // number of tasks processed on this machine
    Job activeJob; // job currently active on this machine

    // constructor
    public Machine() {
        jobQ = new LinkedQueue();
    }
    
    // tells the jobQ is empty or not.
    public boolean jobQIsEmpty(){
    	return getJobQ().isEmpty();
    }
    
    // 
    public int workOnJob() {
		activeJob = (Job) getJobQ().remove();
		totalWait += MachineShopSimulator.getCurrentTime() - activeJob.getArrivalTime();
		numTasks++;
		return activeJob.removeNextTask();
	}
    public Job getActiveJob(){
    	return activeJob;
    }

	public void clearActiveJob() {
		activeJob = null;
		
	}

	private LinkedQueue getJobQ() {
		return jobQ;
	}
	
	public void putInJobQ(Job theJob) {
		this.getJobQ().put(theJob);
	}

	int getChangeTime() {
		return changeTime;
	}

	void setChangeTime(int changeTime) {
		this.changeTime = changeTime;
	}

	int getTotalWait() {
		return totalWait;
	}
}
