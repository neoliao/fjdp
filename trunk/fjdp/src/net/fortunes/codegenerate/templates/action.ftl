package ${packagePrefix}.action;

import java.text.ParseException;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.fortunes.admin.helper.AdminHelper;
import net.fortunes.core.action.GenericAction;
import ${packagePrefix}.model.${modelName};
import ${packagePrefix}.service.${modelName}Service;

public class ${modelName}Action extends GenericAction<${modelName}> {
	
	private ${modelName}Service ${modelName?uncap_first}Service;
	
	protected void setEntity(${modelName} e) throws ParseException{
<#list fields as field>
	<#if field.type == "text" || field.type == "textArea">
		e.set${field.name?cap_first}(p("${field.name}"));
	<#elseif field.type == "number">
		e.set${field.name?cap_first}(Integer.paserInt(p("${field.name}")));
	<#elseif field.type == "dict">
		e.set${field.name?cap_first}(AppHelper.toDict(p("${field.name}")));
	<#elseif field.type == "date">
		e.set${field.name?cap_first}(AppHelper.toDate(p("${field.name}")));
	</#if>
</#list> 
	}
	
	protected JSONObject toJsonObject(${modelName} e) throws ParseException{
		AdminHelper record = new AdminHelper();
		record.put("id", e.getId());
<#list fields as field>
		record.put("${field.name}", e.get${field.name?cap_first}());
</#list> 		
		return record.getJsonObject();
	}
	
	
	/*=============== setter and getter =================*/
	
	public void set${modelName}Service(${modelName}Service ${modelName?uncap_first}Service) {
		this.${modelName?uncap_first}Service = ${modelName?uncap_first}Service;
	}

	public ${modelName}Service get${modelName}Service() {
		return ${modelName?uncap_first}Service;
	}

}
