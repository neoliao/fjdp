package misc;

import net.fortunes.admin.model.Dict;
import net.fortunes.admin.model.Employee;
import net.fortunes.admin.model.User;
import net.fortunes.admin.service.DictService;
import net.fortunes.admin.service.EmployeeService;
import net.fortunes.admin.service.UserService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class Test{
	
	public SessionFactory sessionFactory;
	public Session session; 
	
	private EmployeeService employeeService;
	private DictService dictService;
	private UserService userService;
	
	
	public void execute() throws Exception {
		Employee e = new Employee();
		e.setCode("11042");
		employeeService.add(e);
		
		User u = new User();
		u.setName("a32");
		userService.add(u);
		//u.setEmployee(e);
		e.setUser(u);
		

	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-*.xml");
		Test t = (Test)context.getBean("test");
		t.setUp();
		t.execute();
		t.tearDown();
		
		System.out.println(Long.parseLong("0122220"));
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
}


