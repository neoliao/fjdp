package net.fortunes.admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.fortunes.core.Model;

@Entity
public class Employee extends Model{
	
	@Id @GeneratedValue
	private long id;
	
	private String code;
	
	private String name;
	
	private String nickName;
	
	@ManyToOne
	private Dict sex;
	
	@ManyToOne
	private Dict post;
	
	private String email;
	
	private String phone;
	
	private String mobile;
	
	@Column @Temporal(TemporalType.DATE)
	private Date hireDate;
	
	@ManyToOne
	private Dict status;
	
	@OneToOne(mappedBy = "employee")
	private User user;
	
	@ManyToMany(mappedBy = "employees")
	private List<Organization> organizations = new ArrayList<Organization>();
	
    public Employee() {
    }
    
    public Employee(long id) {
    	this.id = id;
    }
    
    public Employee(String code,String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
    	return "员工:"+name;
    }
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Dict getSex() {
		return sex;
	}

	public void setSex(Dict sex) {
		this.sex = sex;
	}

	public Dict getPost() {
		return post;
	}

	public void setPost(Dict post) {
		this.post = post;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Dict getStatus() {
		return status;
	}

	public void setStatus(Dict status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getHireDate() {
		return hireDate;
	}


	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public List<Organization> getOrganizations() {
		return organizations;
	}

}
