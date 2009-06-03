package ${packagePrefix}.model;

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
public class ${modelName} extends Model{
	
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
	
	@Temporal(TemporalType.DATE)
	private Date hireDate;
	
	@ManyToOne
	private Dict status;
	
    public ${modelName}() {
    }
    
    public ${modelName}(long id) {
    	this.id = id;
    }
    
    @Override
	public String toString() {
		return "";
	}
    
    /*===============setter and getter=================*/

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

}
