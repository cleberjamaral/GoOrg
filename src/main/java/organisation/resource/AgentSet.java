package organisation.resource;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import annotations.Feature;
import fit.Resource;
import fit.ResourceSet;

/**
 * @author cleber
 *
 */
public class AgentSet implements ResourceSet {

	private static AgentSet instance = null;
	private Set<Agent> availableAgents = new HashSet<>();

    private AgentSet() {}
    
	public static AgentSet getInstance() 
    { 
        if (instance == null) 
        	instance = new AgentSet();
  
        return instance; 
    }
	
	public void addAgent(Agent agent) {
		availableAgents.add(agent);
	}
	
	public void addAgent(String name) {
		Agent agent = new Agent(name);
		
		availableAgents.add(agent);
	}

	public void addAgent(String name, String id_annotation) {
		Agent agent = new Agent(name);
		agent.addFeature(new Feature(id_annotation));
		
		availableAgents.add(agent);
	}
	
	public void addAnnotationToAgent(String name, String annotation) {
		Iterator<Agent> ag = availableAgents.iterator();
		while (ag.hasNext()) {
			Agent agent = ag.next();
			if (agent.getName().equals(name)) {
				agent.addFeature(new Feature(annotation));
				break;
			}
		}
	}
	
	public Set<Agent> getAvailableAgents() {
		return availableAgents;
	}

	@Override
	public Set<Resource> getResources() {
		// TODO (Set<Resource>) availableAgents; should work!!!
		Set<Resource> resources = new HashSet<>();
		availableAgents.forEach(a -> {resources.add(a);});
		return resources;
	}
}
