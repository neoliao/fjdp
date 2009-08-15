package net.fortunes.jbpm;

import java.util.ArrayList;
import java.util.List;

import net.fortunes.admin.model.Role;
import net.fortunes.admin.service.RoleService;
import net.fortunes.admin.service.UserService;

import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;

/**
 * 本类是jBPM4 IdentitySession的一个实现
 * 与jBPM4 Identity有所不同的是FJDP没有GROUP的概念,可以将FJDP中Role理解成为一个Group
 * 同时net.fortunes.admin.model.User实现了org.jbpm.api.identity.User,
 * net.fortunes.admin.model.Role实现了org.jbpm.api.identity.Group,
 * FJDP中没有Membership这个实体,它们只是一个关联表实现了Role和用户的多对多关系
 * @author Neo.Liao
 *
 */
public class IdentitySessionImpl implements IdentitySession {
	
	private UserService userService;
	private RoleService roleService;

	/** 
	 * 系统中不会用到
	 * @deprecated
	 */
	@Override
	public String createGroup(String groupName, String groupType, String parentGroupId) {
		net.fortunes.admin.model.Role role = new Role();
		role.setName(groupName);
		role.setRoleType(groupType);
		roleService.add(role);
		return String.valueOf(role.getId());
	}
	
	/** 
	 * 系统中不会用到
	 * @deprecated
	 */
	@Override
	public void createMembership(String userId, String groupId, String role) {
		net.fortunes.admin.model.User user = userService.get(userId);
		user.getRoles().add(new Role(Long.parseLong(groupId)));
	}
	
	/** 
	 * 系统中不会用到
	 * @deprecated
	 */
	@Override
	public String createUser(String userName, String firstName, String lastName, String businessEmail) {
		net.fortunes.admin.model.User user = new net.fortunes.admin.model.User();
		user.setName(userName);
		user.setDisplayName(lastName+firstName);
		userService.add(user);
		return String.valueOf(user.getId());
	}
	
	/** 
	 * 系统中不会用到
	 * @deprecated
	 */
	@Override
	public void deleteGroup(String groupId) {
		net.fortunes.admin.model.Role role = roleService.get(groupId);
		try {
			roleService.del(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * 系统中不会用到
	 * @deprecated
	 */
	@Override
	public void deleteMembership(String userId, String groupId, String role) {
		net.fortunes.admin.model.User user = userService.get(userId);
		user.getRoles().remove(roleService.get(groupId));
	}
	
	/** 
	 * 系统中不会用到
	 * @deprecated
	 */
	@Override
	public void deleteUser(String userId) {
		net.fortunes.admin.model.User user = userService.get(userId);
		try {
			userService.del(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Group findGroupById(String groupId) {
		return roleService.get(groupId);
	}
	
	/** 
	 * 得到目标用户所包含的所有组/角色
	 * @param userId 
	 */
	@Override
	public List<Group> findGroupsByUser(String userId) {
		List<Group> jbpmGroups = new ArrayList<Group>();
		List<net.fortunes.admin.model.Role> roles = userService.get(userId).getRoles();
		for(net.fortunes.admin.model.Role role : roles){
			jbpmGroups.add((Group)role);
		}
		return jbpmGroups;
	}

	@Override
	public List<Group> findGroupsByUserAndGroupType(String userId, String groupType) {
		return null;
	}

	@Override
	public User findUserById(String userId) {
		return userService.get(userId);
	}

	@Override
	public List<User> findUsers() {
		List<User> jbpmUsers = new ArrayList<User>();
		List<net.fortunes.admin.model.User> users = userService.getAll();
		for(net.fortunes.admin.model.User user : users){
			jbpmUsers.add((User)user);
		}
		return jbpmUsers;
	}

	@Override
	public List<User> findUsersByGroup(String groupId) {
		List<User> jbpmUsers = new ArrayList<User>();
		List<net.fortunes.admin.model.User> users = roleService.get(groupId).getUsers();
		for(net.fortunes.admin.model.User user : users){
			jbpmUsers.add((User)user);
		}
		return jbpmUsers;
	}

	@Override
	public List<User> findUsersById(String... userIds) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//================= setter and getter ====================
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
