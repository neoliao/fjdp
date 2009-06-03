package net.fortunes.admin.service;

import java.util.List;
import net.fortunes.admin.model.Employee;
import net.fortunes.core.log.annotation.LoggerClass;
import net.fortunes.core.service.GenericService;

@LoggerClass
public class EmployeeService extends GenericService<Employee> {

	public List<Employee> getEmployeesUnAssign() {
		return getDefDao().findByQueryString(
				"from Employee as e where e.user is null");
	}
	
}
