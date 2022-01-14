package organisation.search.cost;

import java.util.List;

import organisation.Parameters;
import organisation.exception.PositionNotFound;
import organisation.goal.GoalNode;
import organisation.position.PositionsTree;

/**
 * @author cleber
 *
 */
public class CostResolver {

	private static List<Cost> preferences = null;

	public CostResolver(List<Cost> preferences) {
		CostResolver.setPreferences(preferences);
	}

	public static List<Cost> getPreferences() {
		return preferences;
	}

	public static void setPreferences(List<Cost> preferences) {
		CostResolver.preferences = preferences;
	}

	public int getPenalty(GoalNode goal, PositionsTree oldTree, PositionsTree newTree) throws PositionNotFound {
		int cost = Parameters.getMinimalPenalty();

		return cost;
	}

}
