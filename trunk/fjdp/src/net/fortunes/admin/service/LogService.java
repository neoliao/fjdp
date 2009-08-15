package net.fortunes.admin.service;

import java.util.Map;

import net.fortunes.admin.model.Log;
import net.fortunes.core.service.GenericService;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

@Component
public class LogService extends GenericService<Log> {
	
	protected Order getOrder(){
		return Order.desc("createTime");
	}
	
	@Override
	protected void setFilter(String query, Map<String, String> queryMap) {
		getDefDao().getHibernateTemplate().enableFilter("contentsFilter")
			.setParameter("contents", "%"+query+"%");
	}
	
}
