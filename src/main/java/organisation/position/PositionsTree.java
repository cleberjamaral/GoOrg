package organisation.position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import annotations.Coordinates;
import fit.Requirement;
import fit.RequirementSet;
import organisation.Parameters;
import organisation.exception.PositionNotFound;
import organisation.goal.GoalNode;
import organisation.goal.GoalTree;

/**
 * @author cleber
 *
 */
public class PositionsTree implements RequirementSet {

	private int numberOfLevels = 0;
	private Set<PositionNode> tree = new HashSet<>();

	public PositionsTree() {
	}

	public int getNumberOfLevels() {
		return numberOfLevels;
	}

	private void setNumberOfLevels(int numberOfLevels) {
		this.numberOfLevels = numberOfLevels;
	}

	public int size() {
		return tree.size();
	}

	public Set<PositionNode> getTree() {
		return tree;
	}

	public void addPositionToTree(PositionNode position) {
		updateNumberOfLevels(position);
		
		tree.add(position);
	}

	private void updateNumberOfLevels(PositionNode position) {
		int levels = 1;
		while (position.getParent() != null) {
			position = position.getParent();
			levels++;
		}

		if (levels > getNumberOfLevels())
			setNumberOfLevels(levels);
	}

	public PositionNode createPosition(PositionNode parent, String name, GoalNode g) {
		PositionNode nr = new PositionNode(parent, name);

		assignGoalToPosition(nr, g);
		addPositionToTree(nr);

		return nr;
	}

	public PositionNode findPositionByName(String positionName) throws PositionNotFound {
		for (PositionNode or : this.tree) {
			if (or.getPositionName().equals(positionName))
				return or;
		}
		throw new PositionNotFound("There is no position with signature = '" + positionName + "'!");
	}

	public PositionsTree cloneContent() throws PositionNotFound {
		PositionsTree clonedTree = new PositionsTree();

		// first clone all positions
		for (PositionNode or : this.tree) {
			PositionNode nnewS = or.cloneContent();
			// not using addPositionToTree because parent is still unknown
			clonedTree.getTree().add(nnewS);
		}

		// finding right parents in the new tree
		for (PositionNode or : clonedTree.getTree()) {

			// it is not the root position
			if (!or.getParentName().equals("")) {
				or.setParent(clonedTree.findPositionByName(or.getParentName()));
			}
		}
		
		// update number of levels after knowning parents
		clonedTree.setNumberOfLevels(1);
		for (PositionNode or : clonedTree.getTree()) {
			clonedTree.updateNumberOfLevels(or);
		}
		
		return clonedTree;
	}

	public PositionNode assignGoalToPositionByPositionName(String positionName, GoalNode newGoal) throws PositionNotFound {
		PositionNode position = this.findPositionByName(positionName);

		assignGoalToPosition(position, newGoal);

		return position;
	}

	public void assignGoalToPosition(PositionNode position, GoalNode newGoal) {
		position.assignGoal(newGoal);

		// Copy all workloads of the goal to this new position
		for (Coordinates w : newGoal.getCoordinates())
			position.addWorkload(w.clone());
	}

	@Override
	public String toString() {
		List<String> signatureByPositions = new ArrayList<>();
		if ((getTree() != null) && (!getTree().isEmpty())) {
			Iterator<PositionNode> iterator = getTree().iterator();
			while (iterator.hasNext()) {
				PositionNode n = iterator.next();
				signatureByPositions.add(n.toString());
			}
			Collections.sort(signatureByPositions);
		}
		return signatureByPositions.toString();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionsTree other = (PositionsTree) obj;
		if (tree == null) {
			if (other.tree != null)
				return false;
		} else if (!tree.toString().equals(other.tree.toString()))
			return false;
		return true;
	}
	
	@Override
	public Set<Requirement> getRequirements() {
		//TODO: (Set<Requirement>) tree should work!!!
		Set<Requirement> requirements = new HashSet<>();
		tree.forEach(r -> {requirements.add(r);});
		return requirements;
	}
}
