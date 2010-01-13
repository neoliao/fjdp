package net.fortunes.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

import net.fortunes.admin.model.Privilege;
import net.fortunes.admin.model.Role;
import net.fortunes.admin.model.User;
import net.fortunes.core.log.annotation.LoggerClass;
import net.fortunes.core.service.GenericService;

@Component
@LoggerClass
public class UserService extends GenericService<User>{
	
	protected DetachedCriteria getConditions(String query,Map<String, String> queryMap) {
		DetachedCriteria criteria = super.getConditions(query, queryMap);
		if(query !=  null){
			criteria.add(Restrictions.or(
					Restrictions.ilike("name", query, MatchMode.ANYWHERE), 
					Restrictions.ilike("displayName", query, MatchMode.ANYWHERE)
			));
		}
		return criteria;
	}
	
	public boolean lockOrUnlockUser(String userId){
		User user = get(userId);
		user.setLocked(user.isLocked()? false : true);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getOnlineUsers(){
		return getDefDao().findByQueryString("from User as u where u.loginSession.logined = true");
	}
	
	public boolean resetPassword(String userId,String password){		
		User user = get(userId);
		user.setPassword(password);
		return true;
	}
	
	public void updateLoginSession(User user, boolean logined){
		user.getLoginSession().setLastLoginTime(new Date());
		user.getLoginSession().setLogined(logined);
		update(user);
	}
	
	/**
	 * 当程序启动时，初始化登陆状态（设置所有用户为未登陆)
	 * 不能使用声明式事务，原因未知
	 */
	@PostConstruct 
	public void initLoginSession(){
		getDefDao().getTransactionTemplate().execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				getDefDao().bulkUpdate("update User as u set u.loginSession.logined = false");
			}
		});
	}
	
	/** 验证用户
	 * @param user
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public User authUser(User user){
		List<User> userList = getDefDao().findByQueryString(
				"from User as u where u.name = ? and u.password = ? and locked = ?", 
				user.getName(),user.getPassword(),false);
		if(userList.size() == 1){
			return userList.get(0);
		}else{
			return null;
		}
	}
	
	public List<Privilege> getPrivileges(User authedUser){
		List<Privilege> userPrivileges = new ArrayList<Privilege>();
		List<Role> roles = authedUser.getRoles();
		for(Role role:roles){
			for(Privilege p:role.getPrivileges()){
				userPrivileges.add(p);
			}
		}	
		return userPrivileges;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUsersByPrivilegeCode(String privilegeCode){
		return getDefDao().findByQueryString(
				"select u from User as u join u.roles as r join r.privileges as p" +
				" where p.code = '"+privilegeCode+"'");
	}

}
