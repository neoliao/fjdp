package net.fortunes.admin.service;

import java.util.List;

import net.fortunes.admin.AdminHelper;
import net.fortunes.admin.model.Employee;
import net.fortunes.admin.model.Menu;
import net.fortunes.admin.model.Organization;
import net.fortunes.core.log.annotation.LoggerClass;
import net.fortunes.core.service.GenericService;

@LoggerClass
public class OrganizationService extends GenericService<Organization> {
	
	private EmployeeService employeeService;
	
	
	@Override
	public Organization add(Organization entity) {
		
		super.add(entity);
		if(entity.getParent() != null)
			entity.getParent().setLeaf(false);
		return entity;
	}
	
	@Override
	public void del(Organization entity) throws Exception {
		Organization parent = entity.getParent();
		super.del(entity);
		if(parent != null && parent.getChildren().size() <= 0)
			parent.setLeaf(true);
	}
	
	public void addEmployee(String organizationId,String employeeId){
		Organization organization = this.get(organizationId);
		Employee employee = employeeService.get(employeeId);
		//在组织中加入员工
		organization.getEmployees().add(employee);
		//将最新关联(添加)的组织设为缺省组织
		employee.setDefaultOrganization(organization);
		
	}
	
	public void removeEmployee(String organizationId,String employeeId){
		Organization organization = this.get(organizationId);
		Employee employee = employeeService.get(employeeId);
		//在组织中移除员工
		organization.getEmployees().remove(employee);
		
		//将要移除的级织为缺省组织时，更改缺省组织为另一个或者设为null
		if(employee.getDefaultOrganization().getId() == organization.getId()){
			List<Organization> organizations = employee.getOrganizations();
			if(organizations != null && organizations.size() > 0){
				employee.setDefaultOrganization(organizations.get(0));
			}else{
				employee.setDefaultOrganization(null);
			}
		}
		
			
	}

	public List<Employee> getUnassignEmployeesByOrganizationId(String organizationId) {
		return getDefDao().findByQueryString(
				" select e from Employee as e left join fetch e.user where e not in " +
				" (select oe from Organization as o join o.employees as oe where o.id =  ?)", 
				Long.parseLong(organizationId));
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}
}
