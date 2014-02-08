package applications;

public class Task {
	// data members
	private int machine;
	private int time;

	// constructor
	public Task(int machine, int time) {
		this.machine = machine;
		this.time = time;
	}

	public int getMachine() {
		return machine;
	}

	public int getTime() {
		return time;
	}
}