package net.fortunes.admin.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import net.fortunes.admin.model.Employee;
import net.fortunes.core.log.annotation.LoggerClass;
import net.fortunes.core.service.GenericService;

@LoggerClass
public class EmployeeService extends GenericService<Employee> {
	
	
	@Override
	protected void setFilter(String query, Map<String, String> queryMap) {
		getDefDao().getHibernateTemplate().enableFilter("employee_queryFilter")
			.setParameter("code", query+"%")
			.setParameter("name", "%"+query+"%");
	}

	public List<Employee> getEmployeesUnAssign() {
		return getDefDao().findByQueryString(
				"from Employee as e where e.user is null");
	}
	
}
