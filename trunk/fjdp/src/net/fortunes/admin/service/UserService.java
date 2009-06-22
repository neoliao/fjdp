package net.fortunes.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import net.fortunes.admin.model.Privilege;
import net.fortunes.admin.model.Role;
import net.fortunes.admin.model.User;
import net.fortunes.core.log.annotation.LoggerClass;
import net.fortunes.core.service.GenericService;
import net.fortunes.util.Tools;

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
	
	public boolean resetPassword(String userId,String password){		
		User user = get(userId);
		user.setPassword(password);
		return true;
	}
	
	/** 验证用户
	 * @param user
	 * @return
	 */
	public User authUser(User user){
		List<User> userList = getDefDao().findByExample(user);
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
	
	public List<User> getUsersByPrivilegeCode(String privilegeCode){
		return getDefDao().findByQueryString(
				"select u from User as u join u.roles as r join r.privileges as p" +
				" where p.code = '"+privilegeCode+"'");
	}

}
