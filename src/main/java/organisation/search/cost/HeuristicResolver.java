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
public class HeuristicResolver {

	private static Cost costFunction = Cost.UNITARY;

	public HeuristicResolver(Cost costFunction) {
		HeuristicResolver.setCostFunction(costFunction);
	}

	public static Cost getCostFunction() {
		return costFunction;
	}

	public static void setCostFunction(Cost costFunction) {
		HeuristicResolver.costFunction = costFunction;
	}

	public int getPedictedCost(List<GoalNode> gSuc, PositionsTree rTree) throws PositionNotFound {
		int predictedCost = Parameters.getMinimalPenalty();

		return predictedCost;
	}

}
