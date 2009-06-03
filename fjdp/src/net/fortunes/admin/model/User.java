package net.fortunes.admin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import net.fortunes.core.Model;

@Entity
public class User extends Model{
	
	@Id @GeneratedValue
	private long id;
	
	private String name;
	
	private String displayName;
	
	private String password;
	
	private boolean locked;
	
	@ManyToMany
	private List<Role> roles = new ArrayList<Role>();
	
	@OneToOne
	private Employee employee;

    public User() {
    }
    
    public User(long id) {
    	this.id = id;
    }

    public User( String name,String password) {
        this.name = name;
        this.password = password;
    }
    
    public User( String name,String password,String displayName) {
        this.name = name;
        this.password = password;
        this.displayName = displayName;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getRoles() {
		return roles;
	}
	
	public String toString() {
		return "用户:"+name;
	}

}


