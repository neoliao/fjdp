package misc;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;

public class JbpmTest {

	public static void main(String[] args) {
		 ProcessEngine processEngine = new Configuration().buildProcessEngine();
		 RepositoryService repositoryService = processEngine.getRepositoryService();
		 long deploymentDbid = repositoryService.createDeployment()
	        .addResourceFromClasspath("demo.jpdl.xml")
	        .deploy();
		 System.out.println(deploymentDbid);
		 repositoryService.deleteDeployment(deploymentDbid);
	}
	
}
