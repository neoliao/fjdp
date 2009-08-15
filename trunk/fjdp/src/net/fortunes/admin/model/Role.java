package net.fortunes.admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import net.fortunes.core.Model;

import org.jbpm.api.identity.Group;

@Entity
public class Role extends Model implements Group{
	
	public static final String SYSTEM_ROLE = "system";

	@Id @GeneratedValue
	private long id;
	
	private String name;
	
	private String roleType;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> users = new ArrayList<User>();
	
	@ManyToMany
	private List<Privilege> privileges = new ArrayList<Privilege>();
	
	private String description;

    public Role() {
    }
    
    public Role(long id) {
    	this.id = id;
    }
    
    public Role(String name,String roleType) {
    	this.name = name;
    	this.roleType = roleType;
    }  
    
    @Override
    public String toString() {
    	return "角色:"+name;
    }
    
    //================= jbpm4 Group impl ====================
    @Override
	public String getId() {
		return String.valueOf(this.id);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getType() {
		return this.getRoleType();
	}
	
	//================= setter and getter ====================
	public void setId(long id){
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getRoleType() {
		return roleType;
	}

}
