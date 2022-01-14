package organisation.resource;

import java.util.HashSet;
import java.util.Set;

import annotations.Coordinates;
import fit.Resource;

/**
 * @author cleber
 *
 */
public class Agent implements Resource {
	private String name;
	private Set<Coordinates> coordinates = new HashSet<>();

	public Agent(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Coordinates> getCoordinates() {
		return coordinates;
	}

	public void addCoordinate(Coordinates Coordinate) {
		coordinates.add(Coordinate);
	}
	
	public String toString() {
		return this.name + " " + coordinates;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Agent other = (Agent) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public Set<String> getFeatures() {
		Set<String> features = new HashSet<>();
		coordinates.forEach(s -> {features.add(s.getId());});
		return features;
	}

	@Override
	public String getResource() {
		return getName();
	}

}
