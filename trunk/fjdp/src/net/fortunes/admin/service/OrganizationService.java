package net.fortunes.admin.service;

import java.util.List;

import net.fortunes.admin.helper.AdminHelper;
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
		organization.getEmployees().add(AdminHelper.toEmployee(employeeId));
	}
	
	public void removeEmployee(String organizationId,String employeeId){
		Organization organization = this.get(organizationId);
		organization.getEmployees().remove(AdminHelper.toEmployee(employeeId));
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
