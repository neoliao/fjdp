package net.fortunes.admin.action;

import java.util.List;

import net.fortunes.admin.helper.AdminHelper;
import net.fortunes.admin.model.Privilege;
import net.fortunes.admin.model.Role;
import net.fortunes.admin.model.User;
import net.fortunes.admin.service.PrivilegeService;
import net.fortunes.admin.service.RoleService;
import net.fortunes.admin.service.UserService;
import net.fortunes.core.action.GenericAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

public class RoleAction extends GenericAction<Role> {
	
	private RoleService roleService;
	private UserService userService;
	private PrivilegeService privilegeService;
	private String[] checkedId;
	
	
	protected void setEntity(Role role){
		role.setName(p("nameCn"));
		role.setDescription(p("description"));
	}
	
	protected JSONObject toJsonObject(Role role){
		AdminHelper record = new AdminHelper();
		record.put("id", role.getId());
		record.put("nameCn", role.getName());		
		record.put("description", role.getDescription());
		return record.getJsonObject();
	}

	public String getRolesByUser() throws Exception {
		User user = userService.get(getId());
		List<Role> roleList = roleService.getListData().getList();
		JSONArray ja = new JSONArray();
		for(Role role:roleList){
			JSONObject record = new JSONObject();
			record.put("id", role.getId());
			record.put("text", role.getName());
			record.put("checked",user == null?false : user.getRoles().contains(role));
			ja.add(record);
		}
		jo.put("data", ja);
		return render(jo);
	}
	
	public String listPrivileges() throws Exception {
		Privilege rootPrivilege = privilegeService.getRoot();
		Role role = roleService.get(id);
		JSONArray ja = walkPrivilegeTree(rootPrivilege,role);	
		return render(ja);
	}
	
	public String updatePrivileges() throws Exception{
		roleService.updatePrivileges(id, getCheckedId());
		return render(jo);
	}
	
	private JSONArray walkPrivilegeTree(Privilege privilege,Role role){
		JSONArray ja = new JSONArray();
		List<Privilege> ps = privilege.getChildren();
		for (Privilege p : ps) {
			JSONObject jo = new JSONObject();
			jo.put("id", p.getId());  
			jo.put("checked", role.getPrivileges().contains(p));
			jo.put("text", p.getText());
			
			if (p.getChildren().isEmpty()) {
				jo.put("leaf", true);
				if(p.getCode().endsWith("view")){
					jo.put("qtip", "当有其它权限被选中时,浏览权限必须是选中状态");
					JSONObject listenerJo = new JSONObject();
					JSONFunction function = new JSONFunction(new String[]{"node","checked"},
							"if(checked == false){"+
							"  node.parentNode.ui.toggleCheck(false);"+
							"}"
					);
					listenerJo.put("checkchange", function);
					jo.put("listeners", listenerJo);
				}else{
					JSONObject listenerJo = new JSONObject();
					JSONFunction function = new JSONFunction(new String[]{"node","checked"},
							"if(checked == true){"+
							"  node.parentNode.firstChild.ui.toggleCheck(true);"+
							"}"
					);
					listenerJo.put("checkchange", function);
					jo.put("listeners", listenerJo);
				}
			} else {
				jo.put("expanded", true);
				jo.put("children",walkPrivilegeTree(p,role));				
			}
			ja.add(jo);
		}
		return ja;
	}
	
	public String[] getCheckedId() {
		return checkedId;
	}

	public void setCheckedId(String[] checkedId) {
		this.checkedId = checkedId;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}
	

}
