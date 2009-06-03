package net.fortunes.admin.service;

import net.fortunes.admin.model.Privilege;
import net.fortunes.admin.model.Role;
import net.fortunes.core.log.annotation.LoggerClass;
import net.fortunes.core.service.GenericService;

@LoggerClass
public class RoleService extends GenericService<Role> {
	
	public void updatePrivileges(String roleId,String[] privilegeIds){
		Role role = this.get(roleId);
		for(String privilegeId : privilegeIds){
			role.getPrivileges().add(new Privilege(Long.parseLong(privilegeId)));
		}
	}
}
