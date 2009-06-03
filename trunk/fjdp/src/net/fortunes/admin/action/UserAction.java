package net.fortunes.admin.action;

import java.util.List;

import net.fortunes.admin.helper.AdminHelper;
import net.fortunes.admin.model.Role;
import net.fortunes.admin.model.User;
import net.fortunes.admin.service.UserService;
import net.fortunes.core.action.GenericAction;
import net.fortunes.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

public class UserAction extends GenericAction<User> {
	
	private UserService userService;
	
	protected void setEntity(User user){
		user.setName(p("userName"));
		user.setDisplayName(p("userDisplayName"));
		user.setEmployee(AdminHelper.toEmployee(p("employee")));
		//修改时不改变密码
		if(p("password")!=null)
			user.setPassword(Tools.encodePassword(p("password")));
		
		user.getRoles().clear();
		if(StringUtils.isNotEmpty(p("roles"))){
			for(String roleId : p("roles").split(",")){				
				user.getRoles().add(AdminHelper.toRole(roleId));
			}
		}
	}
	
	protected JSONObject toJsonObject(User user){
		AdminHelper record = new AdminHelper();
		record.put("id", user.getId());
		record.put("userName", user.getName());
		record.put("userDisplayName", user.getDisplayName());
		record.put("password", user.getPassword());
		record.put("password2", user.getPassword());
		record.put("employee", user.getEmployee());
		record.put("locked", user.isLocked());
		
		JSONArray ja = new JSONArray();
		for(Role role : user.getRoles()){
			ja.add(new AdminHelper().put("role", role));
		}
		record.put("roles", ja);
		return record.getJsonObject();
	}
	
	public String getUsers() throws Exception{
		List<User> userList = getDefService().getListData().getList();
		JSONArray ja = new JSONArray();
		for(User user:userList){
			JSONObject record = new JSONObject();
			record.put("id", user.getId());
			record.put("text", user.getEmployee().getName());
			ja.add(record);
		}
		jo.put("data", ja);
		return render(jo); 
	}
	
	public String lockUser() throws Exception{
		boolean flag = userService.lockOrUnlockUser(getId());
		jo.put("success", flag);
		return render(jo);
	}
	
	public String resetPassword() throws Exception{
		boolean flag = userService.resetPassword(getId(),Tools.encodePassword(p("password")));
		jo.put("success", flag);
		return render(jo);
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
}
