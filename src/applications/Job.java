package applications;

import dataStructures.LinkedQueue;

public class Job {
    // data members
    private LinkedQueue taskQ; // this job's tasks
    public int length; // sum of scheduled task times
    public int arrivalTime; // arrival time at current queue
    private int id; // job identifier

    // constructor
    public Job(int theId) {
        id = theId;
        taskQ = new LinkedQueue();
        // length and arrivalTime have default value 0
    }

    // other methods
    public void addTask(int theMachine, int theTime) {
        getTaskQ().put(new Task(theMachine, theTime));
    }

    /**
     * remove next task of job and return its time also update length
     */
    public int removeNextTask() {
        int theTime = ((Task) getTaskQ().remove()).time;
        length += theTime;
        return theTime;
    }

	public int getId() {
		return id;
	}

	public LinkedQueue getTaskQ() {
		return taskQ;
	}
}
