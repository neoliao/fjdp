package misc;

import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.fortunes.admin.model.Employee;
import net.fortunes.admin.model.Organization;
import net.fortunes.admin.model.Privilege;
import net.fortunes.admin.model.Role;
import net.fortunes.admin.model.User;
import net.fortunes.admin.model.Config.ConfigKey;
import net.fortunes.admin.service.ConfigService;
import net.fortunes.admin.service.DictService;
import net.fortunes.admin.service.EmployeeService;
import net.fortunes.admin.service.MenuService;
import net.fortunes.admin.service.OrganizationService;
import net.fortunes.admin.service.PrivilegeService;
import net.fortunes.admin.service.RoleService;
import net.fortunes.admin.service.UserService;
import net.fortunes.util.Tools;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.jbpm.api.RepositoryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;


public class InitDb {
	
	public static final String DICT_XML_PATH = "/dict.xml";
	public static final String FUNC_XML_PATH = "/function.xml";
	
	public SessionFactory sessionFactory;
	private AnnotationSessionFactoryBean annotationSessionFactoryBean; 
	public Session session; 
	
	public PrivilegeService privilegeService;
	public EmployeeService employeeService;
	public OrganizationService organizationService;
	public RoleService roleService;
	public UserService userService;
	public DictService dictService;
	public MenuService menuService;
	public ConfigService configService;
	
	private RepositoryService repositoryService;
	
	public void execute() throws Exception{
		Configuration conf = annotationSessionFactoryBean.getConfiguration();
		annotationSessionFactoryBean.createDatabaseSchema();
		/*SchemaExport dbExport=new SchemaExport(conf);
		dbExport.create(true, true);*/
		doInitDb();
	}
	
	private void doInitDb() throws Exception{
		menuService.initToDb(new InputStreamReader(
				InitDb.class.getResourceAsStream(FUNC_XML_PATH),"utf-8"));
		dictService.initToDb(new InputStreamReader(
				InitDb.class.getResourceAsStream(DICT_XML_PATH),"utf-8"));
		
		//初始化系统参数
		Map<ConfigKey, String> maps = new EnumMap<ConfigKey, String>(ConfigKey.class);
		maps.put(ConfigKey.APP_ROOT_DIR, "/home/weblogic");
		maps.put(ConfigKey.ADMIN_EMAIL, "admin@sz.pbc.org.cn");
		maps.put(ConfigKey.TEMP_UPLOAD_DIR, "/upload");
		maps.put(ConfigKey.NON_HOUSE_PAY_RATE, "50");
		maps.put(ConfigKey.HOUSE_PAY_RATE, "40");
		maps.put(ConfigKey.ALERT_PERCENT, "0.3");
		maps.put(ConfigKey.FULL_PAY_DEVIATION_PERCENT, "0.03");
		configService.initConfigs(maps);
		
		//新建权限
		List<Privilege> pList = privilegeService.getListData().getList();	
		
		//新建角色
		Role admin = new Role("系统管理员",Role.SYSTEM_ROLE);
		Role typeMan = new Role("业务人员",Role.SYSTEM_ROLE);
		Role feedReader = new Role("业务审批人员",Role.SYSTEM_ROLE);
		roleService.add(admin);
		roleService.add(typeMan);
		roleService.add(feedReader);
		
		//关联角色和权限
		admin.setPrivileges(pList);
	
		//新建员工
		Employee adminEmployee = new Employee("00","超级管理员");
		employeeService.add(adminEmployee);
		
		//新建用户 
		User adminUser = new User("admin",Tools.encodePassword("admin"),adminEmployee.getName());
		
		//关联用户和角色,用户和员工
		adminUser.setEmployee(adminEmployee);
		
		adminUser.getRoles().add(admin);
		
		userService.add(adminUser);
		
		//新建部门 
		Organization root = new Organization(null,"组织root","");
		organizationService.add(root);
		
		Organization deparment1 = new Organization(root,"维修资金管理中心","维修资金管理中心");
		organizationService.add(deparment1);
		
		Organization deparment2 = new Organization(root,"莞城房管所","莞城房管所");
		organizationService.add(deparment2);
		
		Organization deparment11 = new Organization(root,"东城房管所","东城房管所");
		organizationService.add(deparment11);
		
		//importAreas(InitDb.class.getResourceAsStream("/areas.xls"));
		
		//发布两个业务流程
		//repositoryService.createDeployment().addResourceFromClasspath("process/refundApply.jpdl.xml").deploy();
		//repositoryService.createDeployment().addResourceFromClasspath("process/batchRefund.jpdl.xml").deploy();

	}
	
	public InitDb() {
	}
	
	protected void setUp(){
		session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(session));
	}
	
	protected void tearDown(){
		TransactionSynchronizationManager.unbindResource(sessionFactory);  
		SessionFactoryUtils.releaseSession(session, sessionFactory);  
	}
	
	public static void main(String[] args) throws Exception {
		try {
			String[] configFiles = {"spring-core.xml","spring-server.xml"};
			ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
			InitDb intiDb = (InitDb)context.getBean("initDb");
			intiDb.setAnnotationSessionFactoryBean(
					(AnnotationSessionFactoryBean)context.getBean("&sessionFactory"));
			intiDb.setUp();
			intiDb.execute();
			intiDb.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.exit(0);
	}
	
	//========================= setter and getter ==========================
	
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

	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}


	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}


	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setAnnotationSessionFactoryBean(
			AnnotationSessionFactoryBean annotationSessionFactoryBean) {
		this.annotationSessionFactoryBean = annotationSessionFactoryBean;
	}

	public AnnotationSessionFactoryBean getAnnotationSessionFactoryBean() {
		return annotationSessionFactoryBean;
	}


}
