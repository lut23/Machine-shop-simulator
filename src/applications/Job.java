package applications;

import dataStructures.LinkedQueue;

public class Job {
    // data members
    public LinkedQueue taskQ; // this job's tasks
    public int length; // sum of scheduled task times
    public int arrivalTime; // arrival time at current queue
    public int id; // job identifier

    // constructor
    public Job(int theId) {
        id = theId;
        taskQ = new LinkedQueue();
        // length and arrivalTime have default value 0
    }

    // other methods
    public void addTask(int theMachine, int theTime) {
        taskQ.put(new Task(theMachine, theTime));
    }

    /**
     * remove next task of job and return its time also update length
     */
    public int removeNextTask() {
        int theTime = ((Task) taskQ.remove()).time;
        length += theTime;
        return theTime;
    }
}
