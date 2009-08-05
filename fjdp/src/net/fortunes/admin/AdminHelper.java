package net.fortunes.admin;

import net.fortunes.admin.model.Organization;
import net.fortunes.admin.model.Dict;
import net.fortunes.admin.model.Employee;
import net.fortunes.admin.model.Role;
import net.fortunes.admin.model.User;
import net.fortunes.core.Helper;
import net.sf.json.JSONObject;

public class AdminHelper extends Helper{
	
	/**
	 * @param key
	 * @param dict
	 * @return
	 */
	public JSONObject put(String key,Dict dict){
		JSONObject jo = new JSONObject();
		if(dict != null){
			jo.put("id", dict.getId());
			jo.put("text", dict.getText());
			jsonObject.put(key, jo);
		}
		
		return jo;
	}
	
	public JSONObject put(String key,Employee employee){	
		JSONObject jo = new JSONObject();
		if(employee != null){
			jo.put("id", employee.getId());
			jo.put("text", employee.getName());
			jsonObject.put(key, jo);
		}
		return jo;
	}
	
	public JSONObject put(String key,User user){
		JSONObject jo = new JSONObject();
		if(user != null){
			jo.put("id", user.getId());
			jo.put("text", user.getDisplayName());
			jsonObject.put(key, jo);
		}
		
		return jo;
	}
	
	public JSONObject put(String key,Role role){
		JSONObject jo = new JSONObject();
		if(role != null){
			jo.put("id", role.getId());
			jo.put("text", role.getName());
			jsonObject.put(key, jo);
		}
		return jo;
	}
	
	
	public static Employee toEmployee(String id){
		return (id == null || id.equals("")) ? null : new Employee(Long.parseLong(id));
	}
	
	public static Organization toDepartment(String id){
		return (id == null || id.equals("")) ? null : new Organization(Long.parseLong(id));
	}
	
	public static User toUser(String id){
		return (id == null || id.equals("")) ? null : new User(Long.parseLong(id));
	}
	
	public static Role toRole(String id){
		return (id == null || id.equals("")) ? null : new Role(Long.parseLong(id));
	}
	
	public static Dict toDict(String id){
		return (id == null || id.equals("")) ? null : new Dict(id);
	}
	
}
