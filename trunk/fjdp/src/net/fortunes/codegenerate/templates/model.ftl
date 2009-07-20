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
	
<#list fields as field>
<#if field.type == "text">
	private String ${field.name};
	
<#elseif field.type == "textArea">
	@Column(length = ${field.extend!"500"})
	private String ${field.name};
	
<#elseif field.type == "number">
	private int ${field.name};
	
<#elseif field.type == "dict">
	@ManyToOne
	private Dict ${field.name};
	
<#elseif field.type == "date">
	@Temporal(TemporalType.<#if field.extend == "time">TIME<#elseif field.extend == "dateTime">TIMESTAMP<#else>DATE</#if>)
	private Date ${field.name};
	
</#if>
</#list> 
	
    public ${modelName}() {
    }
    
    public ${modelName}(long id) {
    	this.id = id;
    }
    
    @Override
	public String toString() {
		return "";
	}
    
    /*=============== setter and getter =================*/
    
    public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
<#list fields as field>
	public void set${field.name?cap_first}(<#if field.type == "text" || field.type == "textArea">String<#elseif field.type == "dict">Dict<#elseif field.type == "number">int<#else>Date</#if> ${field.name}) {
		this.${field.name} = ${field.name};
	}

	public <#if field.type == "text" || field.type == "textArea">String<#elseif field.type == "dict">Dict<#elseif field.type == "number">int<#else>Date</#if> get${field.name?cap_first}() {
		return ${field.name};
	}
</#list>

}
