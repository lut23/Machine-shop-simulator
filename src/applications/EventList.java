package applications;

public class EventList {
	// data members
	private int[] finishTime; // finish time array

	// constructor
	public EventList(int theNumMachines, int theLargeTime) {// initialize finish times for m machines
		if (theNumMachines < 1)
			throw new IllegalArgumentException(MachineShopSimExceptions.NUMBER_OF_MACHINES_MUST_BE_AT_LEAST_1);
		finishTime = new int[theNumMachines + 1];

		// all machines are idle, initialize with
		// large finish time
		for (int i = 0; i < theNumMachines; i++)
			getFinishTime()[i+1] = theLargeTime;
	}

	/** @return machine for next event */
	public int nextEventMachine() {
		// find first machine to finish, this is the
		// machine with smallest finish time
		int fastestMachineIndex = 1;
		int smallestFinishTime = getFinishTime()[1];
		for (int i = 1; i < getFinishTime().length-1; i++)
			if (getFinishTime()[i+1] < smallestFinishTime) {// i finishes earlier
				fastestMachineIndex = i+1;
				smallestFinishTime = getFinishTime()[i+1];
			}
		return fastestMachineIndex;
	}

	public int nextEventTime(int theMachine) {
		return getFinishTime()[theMachine];
	}

	public void setFinishTime(int theMachine, int theTime) {
		finishTime[theMachine] = theTime;
	}

	int[] getFinishTime() {
		return finishTime;
	}
}
