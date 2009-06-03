package ${packagePrefix}.action;

import java.text.ParseException;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.fortunes.admin.helper.AdminHelper;
import net.fortunes.core.action.GenericAction;
import ${packagePrefix}.model.${modelName};

public class ${modelName}Action extends GenericAction<${modelName}> {
	
	private ${modelName}Service ${modelNameLower}Service;
	
	protected void setEntity(${modelName} e) throws ParseException{
		e.setCode(p("code"));
		e.setStatus(AdminHelper.toDict(p("status")));
		e.setHireDate(AdminHelper.toDate(p("hireDate")));
	}
	
	protected JSONObject toJsonObject(${modelName} e) throws ParseException{
		AdminHelper record = new AdminHelper();
		record.put("id", e.getId());
		record.put("code", e.getCode());		
		return record.getJsonObject();
	}
	
	public void set${modelName}Service(${modelName}Service ${modelNameLower}Service) {
		this.${modelNameLower}Service = ${modelNameLower}Service;
	}

	public ${modelName}Service get${modelName}Service() {
		return ${modelNameLower}Service;
	}

}
