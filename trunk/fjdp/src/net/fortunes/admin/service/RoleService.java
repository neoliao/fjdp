package net.fortunes.admin.service;

import org.springframework.stereotype.Component;

import net.fortunes.admin.model.Privilege;
import net.fortunes.admin.model.Role;
import net.fortunes.core.log.annotation.LoggerClass;
import net.fortunes.core.service.GenericService;

@Component
@LoggerClass
public class RoleService extends GenericService<Role> {
	
	public void updatePrivileges(String roleId,String[] privilegeIds){
		Role role = this.get(roleId);
		role.getPrivileges().clear();
		for(String privilegeId : privilegeIds){
			role.getPrivileges().add(new Privilege(Long.parseLong(privilegeId)));
		}
	}
}
