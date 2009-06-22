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
	protected DetachedCriteria getConditions(String query,Map<String, String> queryMap) {
		DetachedCriteria criteria = super.getConditions(query, queryMap);
		if(query !=  null){
			criteria.add(Restrictions.or(
					Restrictions.ilike("name", query, MatchMode.ANYWHERE), 
					Restrictions.ilike("code", query, MatchMode.START)
			));
		}
		return criteria;
	}

	public List<Employee> getEmployeesUnAssign() {
		return getDefDao().findByQueryString(
				"from Employee as e where e.user is null");
	}
	
}
