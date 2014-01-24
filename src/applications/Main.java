package applications;

public class Main {
	
	public static void main(String[] args) {
		MachineShopSimulator sim = new MachineShopSimulator(Integer.MAX_VALUE, 0);
		
		/**largeTime = Integer.MAX_VALUE;**/
		/*
		 * It's vital that we (re)set this to 0 because if the simulator is called
		 * multiple times (as happens in the acceptance tests), because timeNow
		 * is static it ends up carrying over from the last time it was run. I'm
		 * not convinced this is the best place for this to happen, though.
		 */
		/**timeNow = 0;**/
		sim.inputData(); // get machine and job data
		sim.startShop(); // initial machine loading
		sim.simulate(); // run all jobs through shop
		sim.outputStatistics(); // output machine wait times
	}

}
