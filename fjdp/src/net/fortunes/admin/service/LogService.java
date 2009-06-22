package net.fortunes.admin.service;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.fortunes.admin.model.Log;
import net.fortunes.core.service.GenericService;

public class LogService extends GenericService<Log> {
	
	protected DetachedCriteria getConditions(String query,Map<String, String> queryMap){
		DetachedCriteria criteria = super.getConditions(query, queryMap);
		if(query !=  null){
			criteria.add(Restrictions.ilike("contents", query,MatchMode.ANYWHERE));
		}
		return criteria;
	}
	
	protected Order getOrder(){
		return Order.desc("createTime");
	}
	
}
