package net.fortunes.admin.service;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.fortunes.admin.model.Log;
import net.fortunes.core.service.GenericService;

public class LogService extends GenericService<Log> {
	
	protected DetachedCriteria getConditions(String query,Map<String, String> map){
		return DetachedCriteria.forClass(Log.class)
			.add(Restrictions.ilike("contents", query,MatchMode.ANYWHERE))
			.addOrder(getOrder());
	}
	
	protected Order getOrder(){
		return Order.desc("createTime");
	}
	
}
