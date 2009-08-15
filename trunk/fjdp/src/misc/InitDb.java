package misc;

import java.io.FileInputStream;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


public class InitDb {
	
	public static final String DICT_XML_PATH = "build/classes/dict.xml";
	public static final String FUNC_XML_PATH = "build/classes/function.xml";
	
	public SessionFactory sessionFactory;
	public Session session; 
	
	public PrivilegeService privilegeService;
	public EmployeeService employeeService;
	public OrganizationService organizationService;
	public RoleService roleService;
	public UserService userService;
	public DictService dictService;
	public MenuService menuService;
	public ConfigService configService;
	
	public void execute() throws Exception{
		
		menuService.initToDb(new InputStreamReader(
				new FileInputStream(FUNC_XML_PATH),"UTF-8"));
		dictService.initToDb(new InputStreamReader(
				new FileInputStream(DICT_XML_PATH),"UTF-8"));
		
		//初始化系统参数
		Map<ConfigKey, String> maps = new EnumMap<ConfigKey, String>(ConfigKey.class);
		maps.put(ConfigKey.ADMIN_EMAIL, "admin@sz.pbc.org.cn");
		configService.updateConfigs(maps);
		
		//新建权限
		List<Privilege> pList = privilegeService.getListData().getList();	
		
		//新建角色
		Role admin = new Role("系统管理员",Role.SYSTEM_ROLE);
		Role typeMan = new Role("人行数据录入",Role.SYSTEM_ROLE);
		Role feedReader = new Role("人行反馈审阅人员",Role.SYSTEM_ROLE);
		Role bankMan = new Role("银行用户",Role.SYSTEM_ROLE);
		Role companyMan = new Role("评估公司用户",Role.SYSTEM_ROLE);
		roleService.add(admin);
		roleService.add(typeMan);
		roleService.add(feedReader);
		roleService.add(bankMan);
		roleService.add(companyMan);
		
		//关联角色和权限
		admin.setPrivileges(pList);

		//新建员工
		Employee liao = new Employee("01","廖清平");
		Employee yu = new Employee("02","于涛");
		Employee xinag = new Employee("03","向琰恒");
		Employee zhang = new Employee("04","张歧文");
		Employee yin = new Employee("05","尹厅");
		employeeService.add(liao);
		employeeService.add(yu);
		employeeService.add(xinag);
		employeeService.add(zhang);
		employeeService.add(yin);
		
		//新建用户 
		User lqp = new User(liao.getCode(),Tools.encodePassword("neo"),liao.getName());
		User yutao = new User(yu.getCode(),Tools.encodePassword("neo"),yu.getName());
		User xyh = new User(xinag.getCode(),Tools.encodePassword("neo"),xinag.getName());
		User zqw = new User(zhang.getCode(),Tools.encodePassword("neo"),zhang.getName());
		User yt = new User(yin.getCode(),Tools.encodePassword("neo"),yin.getName());
		
		//关联用户和角色,用户和员工
		lqp.setEmployee(liao);
		yutao.setEmployee(yu);
		xyh.setEmployee(xinag);
		zqw.setEmployee(zhang);
		yt.setEmployee(yin);
		
		lqp.getRoles().add(admin);
		yutao.getRoles().add(typeMan);		
		xyh.getRoles().add(feedReader);
		zqw.getRoles().add(bankMan);
		yt.getRoles().add(bankMan);
		
		userService.add(lqp);
		userService.add(yutao);
		userService.add(xyh);
		userService.add(zqw);
		userService.add(yt);
		
		//新建部门 
		Organization root = new Organization(null,"组织root","");
		organizationService.add(root);
		
		Organization deparment1 = new Organization(root,"招商银行深圳分行","招商银行");
		organizationService.add(deparment1);
		
		Organization deparment2 = new Organization(root,"建设银行深圳分行","建设银行");
		deparment2.getEmployees().add(zhang);
		organizationService.add(deparment2);
		
		Organization deparment11 = new Organization(deparment1,"招商银行天安支行","天安支行");
		deparment11.setParent(deparment1);
		deparment11.getEmployees().add(yin);
		organizationService.add(deparment11);
		
		/*//新建企业信息
		Enterprise sony = new Enterprise();
		sony.setName("深圳信天喜科技公司");
		sony.setOrganizationNumber("0755001-01");
		enterpriseService.add(sony);
		
		Enterprise fuji = new Enterprise();
		fuji.setName("深圳富士精工有限公司");
		fuji.setOrganizationNumber("0755001-02");
		enterpriseService.add(fuji);*/
		
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
		String configFile = "spring-*.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configFile);
		InitDb intiDb = (InitDb)context.getBean("initDb");
		intiDb.setUp();
		intiDb.execute();
		intiDb.tearDown();
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

}
