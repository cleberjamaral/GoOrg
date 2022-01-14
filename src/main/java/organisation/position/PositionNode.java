package organisation.position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import annotations.Coordinates;
import fit.Requirement;
import organisation.goal.GoalNode;

/**
 * @author cleber
 *
 */
public class PositionNode implements Requirement {
	// positionName and parentName are unique names for this position and its parent in this tree (ex: r0, r1...)
	private String positionName;
	private String parentName;
	private String type;

	private PositionNode parent;
	private List<PositionNode> descendants = new ArrayList<>();
	private Set<Coordinates> workloads = new HashSet<>();
	private Set<GoalNode> assignedGoals = new HashSet<>();


	public PositionNode(PositionNode parent, String positionName) {
		setParent(parent);
		this.positionName = positionName;
	}
	
	public void addWorkload(Coordinates workload) {
		Coordinates w = getWorkload(workload.getId());
		if (w != null) {
			w.setX((double) w.getX() + (double) workload.getX());
		} else {
			this.workloads.add(workload);
		}
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}

	public Coordinates getWorkload(String id) {
		for (Coordinates w : this.workloads) 
			if (w.getId().equals(id)) return w;
		
		return null;
	}
	
	public Set<Coordinates> getWorkloads() {
		return this.workloads;
	}

	public double getSumWorkload() {
		double sumEfforts = 0;
		for (Coordinates w : getWorkloads())
			sumEfforts += (double) w.getX();
		return sumEfforts;
	}
	
	public void assignGoal(GoalNode g) {
		this.assignedGoals.add(g);
	}

	public Set<GoalNode> getAssignedGoals() {
		return this.assignedGoals;
	}

	private void addDescendant(PositionNode newDescendant) {
		this.descendants.add(newDescendant);
	}

	public List<PositionNode> getDescendants() {
		return this.descendants;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public PositionNode getParent() {
		return this.parent;
	}

	public String getParentName() {
		return this.parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setParent(PositionNode parent) {
		this.parent = parent;
		if (getParent() != null) {
			setParentName(parent.getPositionName());
			getParent().addDescendant(this);
		} else {
			setParentName("");
		}
	}

	/**
	 * Check if this position has a goal which is sibling of the given goal
	 * @param g
	 * @return
	 */
	public boolean hasSiblingGoal(GoalNode g) {
		if (getParent() == null)
			return false;
		return getParent().getAssignedGoals().contains(g.getParent());
	}

	/**
	 * Check if this position has a goal which is sibling of the given goal
	 * @param g
	 * @return
	 */
	public boolean hasParentGoal(GoalNode g) {
		return getAssignedGoals().contains(g.getParent());
	}

	/**
	 * Generate a signature of this position which will be used to make a signature of
	 * the tree which makes a search state unique
	 */
	public String toString() {
		String r = "";

		List<String> signatureByGoals = new ArrayList<>();
		if ((getAssignedGoals() != null) && (!getAssignedGoals().isEmpty())) {
			Iterator<GoalNode> iterator = getAssignedGoals().iterator(); 
			while (iterator.hasNext()) {
				GoalNode n = iterator.next(); 
				signatureByGoals.add(n.getOriginalName());
			}
			Collections.sort(signatureByGoals);
		}
		r += "G{" + signatureByGoals + "}T{" + getType() + "}";

		if (getParent() != null) {
			r += "^";
			r += getParent().toString();
		}
		
		return r;
	}
	
	public PositionNode cloneContent() {
		// parent is not cloned it must be resolved by the tree
		PositionNode clone = new PositionNode(null, getPositionName());
		// parent is resolved by its cloned source parent's name
		clone.setParentName(getParentName());
		clone.setType(this.type);
		for (Coordinates w : getWorkloads()) 
			clone.addWorkload(w.clone());

		for (GoalNode goal : getAssignedGoals()) 
			if (!clone.getAssignedGoals().contains(goal)) 
				clone.getAssignedGoals().add(goal);

	    return clone;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAssignedGoals() == null) ? 0 : getAssignedGoals().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionNode other = (PositionNode) obj;
		if (this.getType() != other.getType())
			return false;
		
		if (this.getAssignedGoals() == null) {
			if (other.getAssignedGoals() != null)
				return false;
		} else if (!getAssignedGoals().equals(other.getAssignedGoals()))
			return false;
		return true;
	}
	
	public boolean containsGoalByOriginalName(GoalNode g1) {
		Iterator<GoalNode> i = assignedGoals.iterator();
		while (i.hasNext()) {
			GoalNode g2 = i.next();
			if (g2.getOriginalName().equals(g1.getOriginalName()))
				return true;
		}
		return false;
    }

	@Override
	public Set<String> getFeatures() {
		Set<String> features = new HashSet<>();
		getWorkloads().forEach(w -> {features.add(w.getId());});
		return features;
	}

	@Override
	public String getRequirement() {
		return getPositionName();
	}
}