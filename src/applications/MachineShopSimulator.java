/** machine shop simulation */

package applications;

import utilities.MyInputStream;
import dataStructures.LinkedQueue;
import exceptions.MyInputException;

public class MachineShopSimulator {

	
	
	
	// data members of MachineShopSimulator
	private static int timeNow; // current time
	private static int numMachines; // number of machines
	private static int numJobs; // number of jobs
	private static EventList eList; // pointer to event list
	private static Machine[] machine; // array of machines
	private static int largeTime; // all machines finish before this
	
	//constructor for MachineShopSimulator
		public MachineShopSimulator(int inputLargeTime, int inputTimeNow) {
			this.timeNow = inputTimeNow;
			this.numMachines = numMachines;
			this.numJobs = numJobs;
			this.eList = eList;
			this.machine = machine;
			this.largeTime = inputLargeTime;
		}
	
	// methods
	/**
	 * move theJob to machine for its next task
	 * 
	 * @return false iff no next task
	 */
	static boolean moveToNextMachine(Job theJob) {
		if (theJob.taskQ.isEmpty()) {// no next task
			System.out.println("Job " + theJob.id + " has completed at "
					+ timeNow + " Total wait was " + (timeNow - theJob.length));
			return false;
		} else {// theJob has a next task
			// get machine for next task
			int p = ((Task) theJob.taskQ.getFrontElement()).machine;
			// put on machine p's wait queue
			machine[p].jobQ.put(theJob);
			theJob.arrivalTime = timeNow;
			// if p idle, schedule immediately
			if (eList.nextEventTime(p) == largeTime) {// machine is idle
				changeState(p);
			}
			return true;
		}
	}

	/**
	 * change the state of theMachine
	 * 
	 * @return last job run on this machine
	 */
	static Job changeState(int theMachine) {// Task on theMachine has finished,
		// schedule next one.
		Job lastJob;
		if (machine[theMachine].activeJob == null) {// in idle or change-over
			// state
			lastJob = null;
			// wait over, ready for new job
			if (machine[theMachine].jobQ.isEmpty()) // no waiting job
				eList.setFinishTime(theMachine, largeTime);
			else {// take job off the queue and work on it
				machine[theMachine].activeJob = (Job) machine[theMachine].jobQ
						.remove();
				machine[theMachine].totalWait += timeNow
						- machine[theMachine].activeJob.arrivalTime;
				machine[theMachine].numTasks++;
				int t = machine[theMachine].activeJob.removeNextTask();
				eList.setFinishTime(theMachine, timeNow + t);
			}
		} else {// task has just finished on machine[theMachine]
			// schedule change-over time
			lastJob = machine[theMachine].activeJob;
			machine[theMachine].activeJob = null;
			eList.setFinishTime(theMachine, timeNow
					+ machine[theMachine].changeTime);
		}

		return lastJob;
	}

	/** input machine shop data */
	static void inputData() {
		// define the input stream to be the standard input stream
		MyInputStream keyboard = new MyInputStream();

		System.out.println("Enter number of machines and jobs");
		numMachines = keyboard.readInteger();
		numJobs = keyboard.readInteger();
		if (numMachines < 1 || numJobs < 1)
			throw new MyInputException(MachineShopSimExceptions.NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1);

		// create event and machine queues
		eList = new EventList(numMachines, largeTime);
		machine = new Machine[numMachines + 1];
		for (int i = 1; i <= numMachines; i++)
			machine[i] = new Machine();

		// input the change-over times
		inputChangeOverTimes(keyboard);

		// input the jobs
		inputJobs(keyboard);
	}

	private static void inputJobs(MyInputStream keyboard) {
		Job theJob;
		for (int i = 1; i <= numJobs; i++) {
			System.out.println("Enter number of tasks for job " + i);
			int tasks = keyboard.readInteger(); // number of tasks
			int firstMachine = 0; // machine for first task
			if (tasks < 1)
				throw new MyInputException(MachineShopSimExceptions.EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK);

			// create the job
			theJob = new Job(i);
			System.out.println("Enter the tasks (machine, time)"
					+ " in process order");
			firstMachine = getTasks(keyboard, theJob, tasks, firstMachine); 
			// task queue
			machine[firstMachine].jobQ.put(theJob);
		}
	}

	private static int getTasks(MyInputStream keyboard, Job theJob, int tasks,
			int firstMachine) {
		for (int j = 1; j <= tasks; j++) {// get tasks for job i
			int theMachine = keyboard.readInteger();
			int theTaskTime = keyboard.readInteger();
			if (theMachine < 1 || theMachine > numMachines
					|| theTaskTime < 1)
				throw new MyInputException(MachineShopSimExceptions.BAD_MACHINE_NUMBER_OR_TASK_TIME);
			if (j == 1)
				firstMachine = theMachine; // job's first machine
			theJob.addTask(theMachine, theTaskTime); // add to
		}
		return firstMachine;
	}

	private static void inputChangeOverTimes(MyInputStream keyboard) {
		System.out.println("Enter change-over times for machines");
		for (int j = 1; j <= numMachines; j++) {
			int ct = keyboard.readInteger();
			if (ct < 0)
				throw new MyInputException(MachineShopSimExceptions.CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0);
			machine[j].changeTime = ct;
		}
	}

	/** load first jobs onto each machine */
	static void startShop() {
		for (int p = 1; p <= numMachines; p++)
			changeState(p);
	}

	/** process all jobs to completion */
	static void simulate() {
		while (numJobs > 0) {// at least one job left
			int nextToFinish = eList.nextEventMachine();
			timeNow = eList.nextEventTime(nextToFinish);
			// change job on machine nextToFinish
			Job theJob = changeState(nextToFinish);
			// move theJob to its next machine
			// decrement numJobs if theJob has finished
			if (theJob != null && !moveToNextMachine(theJob))
				numJobs--;
		}
	}

	/** output wait times at machines */
	static void outputStatistics() {
		System.out.println("Finish time = " + timeNow);
		for (int p = 1; p <= numMachines; p++) {
			System.out.println("Machine " + p + " completed "
					+ machine[p].numTasks + " tasks");
			System.out.println("The total wait time was "
					+ machine[p].totalWait);
			System.out.println();
		}
	}
}
