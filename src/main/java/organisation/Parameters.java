package organisation;

/**
 * @author cleber
 *
 */
public class Parameters {

	private static Parameters instance = null;
	
	// stop search after finding one solution
	private static boolean oneSolution = false;

	// Minimal penalty for creating a new state
	private static int minimalPenalty = 1;
	// Cost penalty used to infer bad decisions on search
	private static int defaultPenalty = 10;

    private Parameters() {}
    
	public static Parameters getInstance() 
    { 
        if (instance == null) 
        	instance = new Parameters();
  
        return instance; 
    }
	
	public static boolean isOneSolution() {
		return oneSolution;
	}

	public static void setOneSolution(boolean oneSolution) {
		Parameters.oneSolution = oneSolution;
	}

	public static int getMinimalPenalty() {
		return minimalPenalty;
	}

	public static int getDefaultPenalty() {
		return defaultPenalty;
	}

	public static void setDefaultPenalty(int defaultPenalty) {
		Parameters.defaultPenalty = defaultPenalty;
	}

}
