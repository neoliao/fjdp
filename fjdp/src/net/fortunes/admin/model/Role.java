package net.fortunes.admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import net.fortunes.core.Model;

@Entity
public class Role extends Model {

	@Id @GeneratedValue
	private long id;
	
	private String name;
	
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
    
    public Role(String name) {
    	this.name = name;
    }
    
    @Override
    public String toString() {
    	return "角色:"+name;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
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

}
