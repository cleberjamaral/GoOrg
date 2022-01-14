package organisation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.apache.commons.io.FileUtils;

import organisation.binder.Binding;
import organisation.goal.GoalNode;
import organisation.goal.GoalTree;
import organisation.position.PositionNode;
import organisation.search.Organisation;

/**
 * @author cleber
 *
 */
public class OrganisationStatistics {
	
	private static OrganisationStatistics instance = null;
	
	int id = 0;
	double originalDataLoad = 0.0;
	double originalWorkLoad = 0.0;
	double minIdle = 0.0;
	String bgTree = "";
	
	List<String> fields = new ArrayList<>();
	
	public static OrganisationStatistics getInstance() 
    { 
        if (instance == null) 
        	instance = new OrganisationStatistics(); 
  
        return instance; 
    } 
	
	private OrganisationStatistics() {
		//fields and sequence of columns in the CSV file
		this.fields.add("id");
		this.fields.add("nPosit");
		this.fields.add("%Feasi"); //Feasible % matching requirements and resources
		this.fields.add("States");
		this.fields.add("pTree");
		this.fields.add("bgTree");
		this.fields.add("agents");
		this.fields.add("matche");
	}
	
	public void prepareGenerationStatisticsFile(final String orgName) {
		id = 0;
		
		createStatisticsFolders();

		try (FileWriter fw = new FileWriter("output/statistics/" + orgName + "_generation.csv", false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.print(StringUtils.join(this.fields, "\t"));
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveGenerationStatistics(final Organisation o) {
		try (FileWriter fw = new FileWriter("output/statistics/" + o.getOrgName() + "_generation.csv", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			
			Map<String, String> line = writeLine(o, null);
			
			out.print("\n");
			for (int i = 0; i < fields.size(); i++) {
				out.print(line.get(fields.get(i)));
				if (i != fields.size()-1) out.print("\t");
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepareBindingStatisticsFile(final String orgName) {
		id = 0;
		
		createStatisticsFolders();

		try (FileWriter fw = new FileWriter("output/statistics/" + orgName + "_binding.csv", false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.print(StringUtils.join(this.fields, "\t"));
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveBindingStatistics(final Organisation o, final Binding binding) {
		try (FileWriter fw = new FileWriter("output/statistics/" + o.getOrgName() + "_binding.csv", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			
			Map<String, String> line = writeLine(o, binding);
			
			out.print("\n");
			for (int i = 0; i < fields.size(); i++) {
				out.print(line.get(fields.get(i)));
				if (i != fields.size()-1) out.print("\t");
			}
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<String, String> writeLine(final Organisation o, final Binding binding) {
		Map<String,String> line = new HashMap<>();
		
		Parameters.getInstance();
		line.put("id", (Integer.toString(++id)));
		line.put("nPosit", (Integer.toString(o.getPositionsTree().getTree().size())));
		
		line.put("pTree", o.getPositionsTree().toString());
		line.put("bgTree", bgTree);
		line.put("States", (Integer.toString(o.getNStates())));

		// Only the binding process sends a set of agents
		if (binding != null) {
			line.put("%Feasi", (String.format("%.0f%%", 100 * binding.getFeasibily())));
			line.put("agents", binding.getAgents().toString());
			line.put("matche", binding.getMatchesAsString());
		}
	
		return line;
	}

	public void saveDataOfBrokenTree() {
		GoalTree gTree = GoalTree.getInstance();
		this.bgTree = gTree.getTree().toString();

		Parameters.getInstance();
	}
	
    private void createStatisticsFolders() {
        // create folders if doesnt exist
		File file = new File("output/statistics/tmp");
        file.getParentFile().mkdirs();
    }

	public void deleteExistingStatistics() {
		try {
			final File filepath = new File("output/statistics");
			FileUtils.deleteDirectory(filepath);

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
