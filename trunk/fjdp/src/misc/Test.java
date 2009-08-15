package misc;

import javax.annotation.Resource;

import net.fortunes.admin.model.Employee;
import net.fortunes.admin.service.DictService;
import net.fortunes.admin.service.EmployeeService;
import net.fortunes.admin.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.pvm.internal.cfg.JbpmConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class Test{
	
	@Resource
	public SessionFactory sessionFactory;
	
	public Session session;
	
	@Resource
	private EmployeeService employeeService;
	private DictService dictService;
	private UserService userService;
	
	private JbpmConfiguration jbpmConfiguration;
	private ProcessEngine processEngine;
	private RepositoryService repositoryService;
	private ExecutionService executionService;
	
	
	public void execute() throws Exception {
		/*String deploymentDbid = repositoryService.createDeployment()
    		.addResourceFromClasspath("process/design.jpdl.xml").deploy();
		
		ProcessInstance processInstance = 
			executionService.startProcessInstanceByKey("design", "order001");*/
		
		Employee employee = new Employee("01","sex");
		employeeService.add(employee);
		
		System.out.println(employeeService.get(String.valueOf(employee.getId())));
		
		employeeService.del(employee);
		
		
		//repositoryService.deleteDeployment(deploymentDbid);
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-core.xml");
		Test t = (Test)context.getBean("test");
		t.setUp();
		t.execute();
		t.tearDown();
		
	}
	
	protected void setUp(){
		session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(session));
	}
	
	protected void tearDown(){
		TransactionSynchronizationManager.unbindResource(sessionFactory);  
		SessionFactoryUtils.releaseSession(session, sessionFactory);  
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public ExecutionService getExecutionService() {
		return executionService;
	}

	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}

	public void setJbpmConfiguration(JbpmConfiguration jbpmConfiguration) {
		this.jbpmConfiguration = jbpmConfiguration;
	}

	public JbpmConfiguration getJbpmConfiguration() {
		return jbpmConfiguration;
	}
}


